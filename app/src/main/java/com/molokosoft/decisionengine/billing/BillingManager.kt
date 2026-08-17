package com.molokosoft.decisionengine.billing

import android.app.Activity
import android.content.Context

import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.PendingPurchasesParams
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.QueryProductDetailsParams
import com.android.billingclient.api.*
import android.util.Log
import com.molokosoft.decisionengine.billing.model.BillingProduct

class BillingManager(context: Context) {
    interface Listener {
        fun onBillingReady()
        fun onProductsLoaded()
        fun onPurchaseAcknowledged(purchase: Purchase)
        fun onPurchaseFailure(billingResult: BillingResult)

        fun onActivePurchasesLoaded(purchases: List<Purchase>)
        fun onError(billingResult: BillingResult)
    }

    private var listener: Listener? =
        null

    fun setListener(listener: Listener) {
        this.listener = listener
    }

    fun clearListener() {
        listener = null
    }

    private var isReady =
        false

    private val productDetails = mutableMapOf<String, ProductDetails>()

    fun queryActiveSubscriptions() {
        if (!isReady)
            return

        val params = QueryPurchasesParams.newBuilder()
            .setProductType(BillingClient.ProductType.SUBS)
            .build()

        billingClient.queryPurchasesAsync(params) { billingResult, purchases ->

            if (billingResult.responseCode != BillingClient.BillingResponseCode.OK) {
                listener?.onError(billingResult)
                return@queryPurchasesAsync
            }

            listener?.onActivePurchasesLoaded(
                purchases.filter {
                    it.purchaseState == Purchase.PurchaseState.PURCHASED
                }
            )
        }
    }

    private fun acknowledgePurchase(purchase: Purchase) {
        val params =
            AcknowledgePurchaseParams
                .newBuilder()
                .setPurchaseToken(
                    purchase.purchaseToken
                )
                .build()

        billingClient.acknowledgePurchase(params) { billingResult ->
            Log.d("Billing", "acknowledge result = ${billingResult.responseCode}")

            if (billingResult.responseCode != BillingClient.BillingResponseCode.OK) {
                listener?.onPurchaseFailure(billingResult)
                return@acknowledgePurchase
            }

            Log.d("Billing", "Calling listener onPurchaseAcknowledged")
            listener?.onPurchaseAcknowledged(purchase)
        }
    }

    private fun handlePurchase(purchase: Purchase) {
        Log.d("Billing", "handlePurchase")

        if (purchase.purchaseState != Purchase.PurchaseState.PURCHASED)
            return

        if (purchase.isAcknowledged) {
            Log.d("Billing", "Already acknowledged")
            listener?.onPurchaseAcknowledged(purchase)
            return
        }

        Log.d("Billing", "Calling acknowledgePurchase")
        acknowledgePurchase(purchase)
    }

    private fun onPurchasesUpdated(
        billingResult: BillingResult,
        purchases: List<Purchase>?
    ) {
        if (billingResult.responseCode != BillingClient.BillingResponseCode.OK)
            return

        purchases?.forEach { purchase ->
            handlePurchase(purchase)
        }
    }

    fun loadProducts(products: List<BillingProduct>) {
        Log.d(
            "Billing",
            "loadProducts called with: $products"
        )

        if (!isReady) {
            Log.e("Billing", "Cannot load products: Billing not ready")
            return
        }

        val validProducts = products
            .map {
                BillingProduct(
                    productId = it.productId.trim(),
                    productType = it.productType
                )
            }
            .filter {
                it.productId.isNotEmpty()
            }

        Log.d(
            "Billing",
            "Valid product IDs: $validProducts"
        )

        if (validProducts.isEmpty()) {
            Log.e(
                "Billing",
                "No valid product IDs supplied"
            )

            return
        }

        Log.d(
            "Billing",
            "Valid product IDs: $validProducts"
        )

        val query =
            QueryProductDetailsParams
                .newBuilder()
                .setProductList(
                    validProducts.map {
                        QueryProductDetailsParams.Product
                            .newBuilder()
                            .setProductId(it.productId)
                            .setProductType(
                                it.productType
                            )
                            .build()
                    }
                )
                .build()

        billingClient.queryProductDetailsAsync(
            query
        ) { billingResult, result ->

            Log.d(
                "Billing",
                "queryProductDetails: " +
                        "code=${billingResult.responseCode}, " +
                        "message=${billingResult.debugMessage}, " +
                        "products=${result.productDetailsList.map {
                            it.productId
                        }}"
            )

            if (billingResult.responseCode != BillingClient.BillingResponseCode.OK) {
                listener?.onError(billingResult)
                return@queryProductDetailsAsync
            }

            val products = result.productDetailsList

            if (products.isEmpty()) {
                Log.e(
                    "Billing",
                    "Google Play returned no products"
                )
                listener?.onError(billingResult)
                return@queryProductDetailsAsync
            }

            productDetails.clear()

            products.forEach {
                productDetails[it.productId] = it
            }

            listener?.onProductsLoaded()
        }
    }

    private val billingClient =
        BillingClient
            .newBuilder(context)
            .setListener { billingResult, purchases ->
                onPurchasesUpdated(
                    billingResult,
                    purchases
                )
            }
            .enablePendingPurchases(
                PendingPurchasesParams
                    .newBuilder()
                    .enableOneTimeProducts()
                    .build()
            )
            .enableAutoServiceReconnection()
            .build()

    fun connect() {
        Log.d(
            "Billing",
            "connect(): " +
                    "clientReady=${billingClient.isReady}, " +
                    "isReady=$isReady"
        )

        if (billingClient.isReady) {
            isReady = true
            listener?.onBillingReady()
            return
        }


        Log.d("Billing", "Starting billing connection")

        billingClient.startConnection(
            object : BillingClientStateListener {

                override fun onBillingSetupFinished(
                    billingResult: BillingResult
                ) {
                    Log.d(
                        "Billing",
                        "Billing setup finished: " +
                                "code=${billingResult.responseCode}, " +
                                "message=${billingResult.debugMessage}"
                    )

                    if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                        isReady = true
                        Log.d("Billing", "Billing ready")
                        listener?.onBillingReady()
                    } else {
                        isReady = false
                        listener?.onError(billingResult)
                    }
                }

                override fun onBillingServiceDisconnected() {
                    Log.d(
                        "Billing",
                        "Billing service disconnected"
                    )

                    isReady = false
                }
            }
        )
    }

    fun getFormattedPrice(productId: String): String? {
        val details =
            productDetails[productId]
                ?: return null

        return details.subscriptionOfferDetails
            ?.firstOrNull()
            ?.pricingPhases
            ?.pricingPhaseList
            ?.firstOrNull()
            ?.formattedPrice
    }

    fun hasFreeTrial(productId: String): Boolean {
        val details =
            productDetails[productId]
                ?: return false

        val trialOffer =
            details
                .subscriptionOfferDetails
                ?.find { offer ->
                    offer.pricingPhases.pricingPhaseList.any { phase ->
                        phase.priceAmountMicros == 0L
                    }
                }

        return trialOffer != null
    }

    fun buyProduct(
        activity: Activity,
        productId: String
    ) {
        if (!billingClient.isReady) {
            Log.e(
                "Billing",
                "Cannot launch billing flow: BillingClient is not ready"
            )

            listener?.onError(
                BillingResult.newBuilder()
                    .setResponseCode(
                        BillingClient.BillingResponseCode.SERVICE_DISCONNECTED
                    )
                    .setDebugMessage(
                        "Billing service is disconnected."
                    )
                    .build()
            )

            return
        }

        val details =
            productDetails[productId]

        if (details == null) {
            Log.e(
                "Billing",
                "Product not found: $productId"
            )

            listener?.onError(
                BillingResult.newBuilder()
                    .setResponseCode(
                        BillingClient.BillingResponseCode.ITEM_UNAVAILABLE
                    )
                    .setDebugMessage(
                        "Product not loaded: $productId"
                    )
                    .build()
            )

            return
        }

        val productDetailsParams =
            BillingFlowParams.ProductDetailsParams
                .newBuilder()
                .setProductDetails(details)

        if (details.productType == BillingClient.ProductType.SUBS) {

            val offer =
                details.subscriptionOfferDetails?.firstOrNull()

            if (offer == null) {
                Log.e(
                    "Billing",
                    "No subscription offer found: $productId"
                )

                return
            }

            Log.d(
                "Billing",
                "Using subscription offer: ${offer.offerToken}"
            )

            productDetailsParams.setOfferToken(
                offer.offerToken
            )
        }

        val billingFlowParams =
            BillingFlowParams
                .newBuilder()
                .setProductDetailsParamsList(
                    listOf(
                        productDetailsParams.build()
                    )
                )
                .build()

        Log.d(
            "Billing",
            "Launching billing flow: " +
                    "product=$productId, " +
                    "type=${details.productType}"
        )

        val result =
            billingClient.launchBillingFlow(
                activity,
                billingFlowParams
            )

        Log.d(
            "Billing",
            "launchBillingFlow: " +
                    "code=${result.responseCode}, " +
                    "message=${result.debugMessage}"
        )

        if (result.responseCode != BillingClient.BillingResponseCode.OK) {
            listener?.onPurchaseFailure(result)
        }
    }
}
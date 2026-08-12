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

class BillingManager(context: Context) {
    interface Listener {
        fun onBillingReady()
        fun onProductsLoaded()
        fun onPurchaseAcknowledged(purchase: Purchase)
        fun onPurchaseFailure(billingResult: BillingResult)

        fun onActivePurchasesLoaded(purchases: List<Purchase>)
        fun onError(billingResult: BillingResult)
    }

    private var listener: Listener? = null

    fun setListener(listener: Listener) {
        this.listener = listener
    }

    fun clearListener() {
        listener = null
    }

    private var isReady = false
        private set

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

    fun loadProducts(productIds: List<String>) {
        if (!isReady)
            return

        val query =
            QueryProductDetailsParams
                .newBuilder()
                .setProductList(
                    productIds.map {
                        QueryProductDetailsParams.Product
                            .newBuilder()
                            .setProductId(it)
                            .setProductType(
                                BillingClient.ProductType.SUBS
                            )
                            .build()
                    }
                )
                .build()

        billingClient.queryProductDetailsAsync(query) { billingResult, result ->

            if (billingResult.responseCode != BillingClient.BillingResponseCode.OK) {
                listener?.onError(billingResult)
                return@queryProductDetailsAsync
            }

            val products = result.productDetailsList

            if (products.isEmpty()) {
                listener?.onError(billingResult)
                return@queryProductDetailsAsync
            }

            productDetails.clear()

            for (product in products)
                productDetails[product.productId] = product

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
        billingClient.startConnection(
            object : BillingClientStateListener {
                override fun onBillingSetupFinished(billingResult: BillingResult) {
                    if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                        isReady = true
                        listener?.onBillingReady()
                    } else {
                        listener?.onError(billingResult)
                    }
                }

                override fun onBillingServiceDisconnected() {
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

    fun buySubscription(
        activity: Activity,
        productID: String
    ) {
        val details =
            productDetails[productID] ?:
                return

        val offer =
            details.subscriptionOfferDetails?.firstOrNull() ?:
                return

        val productDetailsParams =
            BillingFlowParams.ProductDetailsParams
                .newBuilder()
                .setProductDetails(details)
                .setOfferToken(offer.offerToken)
                .build()

        val billingFlowParams =
            BillingFlowParams
                .newBuilder()
                .setProductDetailsParamsList(
                    listOf(productDetailsParams)
                )
                .build()

        val result =
            billingClient.launchBillingFlow(activity, billingFlowParams)

        if (result.responseCode != BillingClient.BillingResponseCode.OK)
            listener?.onPurchaseFailure(result)
    }
}
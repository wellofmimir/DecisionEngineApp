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

class BillingManager(context: Context) {
    interface Listener {
        fun onBillingReady()
        fun onProductsLoaded()
        fun onPurchaseAcknowledged(purchase: Purchase)
        fun onPurchaseFailure(billingResult: BillingResult)
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

    private fun acknowledgePurchase(purchase: Purchase) {
        val params =
            AcknowledgePurchaseParams
                .newBuilder()
                .setPurchaseToken(
                    purchase.purchaseToken
                )
                .build()

        billingClient.acknowledgePurchase(params) { billingResult ->

            if (billingResult.responseCode != BillingClient.BillingResponseCode.OK) {
                listener?.onPurchaseFailure(billingResult)
                return@acknowledgePurchase
            }
        }

        listener?.onPurchaseAcknowledged(purchase)
    }

    private fun handlePurchase(
        purchase: Purchase
    ) {
        if (purchase.purchaseState != Purchase.PurchaseState.PURCHASED)
            return

        if (purchase.isAcknowledged) {
            listener?.onPurchaseAcknowledged(purchase)
            return
        }

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

    fun loadProducts(productIDs: List<String>) {

        if (!isReady)
            return

        val query =
            QueryProductDetailsParams
                .newBuilder()
                .setProductList(
                    productIDs.map {
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
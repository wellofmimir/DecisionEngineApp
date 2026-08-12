package com.molokosoft.decisionengine.billing.model

data class SubscriptionProduct(
    val productId: String,
    val formattedPrice: String,
    val hasFreeTrial: Boolean
)

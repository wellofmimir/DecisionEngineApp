package com.molokosoft.decisionengine.network.backend.model.requests.billing

import kotlinx.serialization.Serializable

@Serializable
data class VerifyPurchaseRequest(
    val purchaseToken: String,
    val productId: String,
    val apiKey: String? = null
)
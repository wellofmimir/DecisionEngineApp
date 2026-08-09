package com.molokosoft.decisionengine.network.backend.model.responses.billing

import kotlinx.serialization.Serializable

@Serializable
data class VerifyPurchaseResponse(
    val apiKey: String
)

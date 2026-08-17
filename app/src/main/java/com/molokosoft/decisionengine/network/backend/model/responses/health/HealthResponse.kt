package com.molokosoft.decisionengine.network.backend.model.responses.health

import kotlinx.serialization.Serializable

@Serializable
data class HealthResponse(
    val status: String
)

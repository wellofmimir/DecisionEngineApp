package com.molokosoft.decisionengine.network.backend.model.responses

import kotlinx.serialization.Serializable

@Serializable
data class ApiError(
    val code: String,
    val message: String
)
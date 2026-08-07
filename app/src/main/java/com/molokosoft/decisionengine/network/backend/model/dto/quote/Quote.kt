package com.molokosoft.decisionengine.network.backend.model.dto.quote

import kotlinx.serialization.Serializable

@Serializable
data class Quote(
    val quote: String,
    val person: String
)

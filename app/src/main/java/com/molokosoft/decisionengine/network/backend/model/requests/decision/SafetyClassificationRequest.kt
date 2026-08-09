package com.molokosoft.decisionengine.network.backend.model.requests.decision

import kotlinx.serialization.Serializable

@Serializable
data class SafetyClassificationRequest(
    val title: String
)
package com.molokosoft.decisionengine.network.backend.model.dto.decision

import kotlinx.serialization.Serializable

@Serializable
data class SafetyClassification(
    val classification: String,
    val reason: String
)


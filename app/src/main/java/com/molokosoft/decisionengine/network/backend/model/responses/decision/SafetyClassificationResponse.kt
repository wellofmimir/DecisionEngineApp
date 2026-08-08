package com.molokosoft.decisionengine.network.backend.model.responses.decision

import kotlinx.serialization.Serializable
import com.molokosoft.decisionengine.network.backend.model.dto.decision.SafetyClassification

@Serializable
data class SafetyClassificationResponse(
    val safetyClassification: SafetyClassification
)

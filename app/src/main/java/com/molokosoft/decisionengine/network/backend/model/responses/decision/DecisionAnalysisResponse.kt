package com.molokosoft.decisionengine.network.backend.model.responses.decision

import com.molokosoft.decisionengine.network.backend.model.dto.decision.DecisionAnalysisResult
import kotlinx.serialization.Serializable

@Serializable
data class DecisionAnalysisResponse(
    val result: DecisionAnalysisResult
)
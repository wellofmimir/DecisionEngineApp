package com.molokosoft.decisionengine.network.backend.model.responses

import kotlinx.serialization.Serializable
import com.molokosoft.decisionengine.network.backend.model.dto.DecisionAnalysisResult
@Serializable
data class DecisionAnalysisResponse(
    val result: DecisionAnalysisResult
)
package com.molokosoft.decisionengine.network.backend.model.requests
import kotlinx.serialization.Serializable
import com.molokosoft.decisionengine.network.backend.model.dto.DecisionOption

@Serializable
data class DecisionAnalysisRequest(
    val decisionTitle: String,
    val recommendedOption: String,
    val options: List<DecisionOption>
)
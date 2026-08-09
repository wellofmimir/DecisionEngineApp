package com.molokosoft.decisionengine.network.backend.model.requests.decision

import com.molokosoft.decisionengine.network.backend.model.dto.decision.DecisionOption
import kotlinx.serialization.Serializable

@Serializable
data class DecisionAnalysisRequest(
    val decisionTitle: String,
    val recommendedOption: String,
    val options: List<DecisionOption>
)
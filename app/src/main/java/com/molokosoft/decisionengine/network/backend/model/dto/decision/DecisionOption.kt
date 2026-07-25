package com.molokosoft.decisionengine.network.backend.model.dto.decision

import com.molokosoft.decisionengine.network.backend.model.dto.decision.DecisionCriterion
import kotlinx.serialization.Serializable

@Serializable
data class DecisionOption(
    val name: String,
    val overallScore: Double,
    val reversibility: Int,
    val criteria: List<DecisionCriterion>
)
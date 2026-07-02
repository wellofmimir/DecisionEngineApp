package com.molokosoft.decisionengine.network.backend.model.dto

import kotlinx.serialization.Serializable

@Serializable
data class DecisionOption(
    val name: String,
    val overallScore: Double,
    val reversibility: Int,
    val criteria: List<DecisionCriterion>
)
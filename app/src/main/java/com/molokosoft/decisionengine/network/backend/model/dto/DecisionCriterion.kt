package com.molokosoft.decisionengine.network.backend.model.dto

import kotlinx.serialization.Serializable

@Serializable
data class DecisionCriterion(
    val name: String,
    val importance: Int,
    val score: Int
)


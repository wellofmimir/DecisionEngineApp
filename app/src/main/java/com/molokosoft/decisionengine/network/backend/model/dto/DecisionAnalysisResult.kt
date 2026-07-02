package com.molokosoft.decisionengine.network.backend.model.dto

import kotlinx.serialization.Serializable

@Serializable
data class DecisionAnalysisResult(
    val summary: String,
    val recommendedOption: String,
    val whyItStandsOut: String,
    val reversibility: String,
    val blindSpots: String,
    val roadmapToSuccess: String,
    val conclusion: String
)


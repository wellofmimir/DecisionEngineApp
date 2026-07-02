package com.molokosoft.decisionengine.repositories.model

import kotlin.math.roundToInt

data class OptionAnalysis(
    val name: String,
    val reversibility: Int,
    val weightedScore: Double,
    val analyses: List<CriterionAnalysis>
) {
    fun weightedScoreAsPercentage(): Int {
        return (weightedScore * 10).roundToInt()
    }
}

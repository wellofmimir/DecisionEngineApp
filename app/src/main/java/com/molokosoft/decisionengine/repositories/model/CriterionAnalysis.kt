package com.molokosoft.decisionengine.repositories.model

data class CriterionAnalysis(
    val name: String,
    val importance: Int,
    val score: Int,
    val contribution: Double,
    val percentage: Double
)

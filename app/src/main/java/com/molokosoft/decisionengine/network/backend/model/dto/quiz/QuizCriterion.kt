package com.molokosoft.decisionengine.network.backend.model.dto.quiz

import kotlinx.serialization.Serializable

@Serializable
data class QuizCriterion(
    val id: String,
    val name: String,
    val description: String,
    val initialWeight: Int = 5
)

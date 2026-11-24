package com.molokosoft.decisionengine.network.backend.model.dto.quiz

import kotlinx.serialization.Serializable

@Serializable
data class Quiz(
    val quizId: String,
    val decisionId: String,
    val title: String,
    val description: String,
    val criteria: List<QuizCriterion>,
    val questions: List<QuizQuestion>
)

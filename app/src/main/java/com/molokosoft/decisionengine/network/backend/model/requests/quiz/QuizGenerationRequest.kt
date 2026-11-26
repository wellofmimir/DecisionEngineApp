package com.molokosoft.decisionengine.network.backend.model.requests.quiz

import kotlinx.serialization.Serializable

@Serializable
data class QuizGenerationRequest(
    val decision: QuizDecisionRequest
)

@Serializable
data class QuizDecisionRequest(
    val id: String,
    val title: String
)

@Serializable
data class QuizOptionRequest(
    val id: String,
    val name: String
)

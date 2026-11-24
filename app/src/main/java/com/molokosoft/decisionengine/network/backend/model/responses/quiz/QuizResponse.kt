package com.molokosoft.decisionengine.network.backend.model.responses.quiz

import com.molokosoft.decisionengine.network.backend.model.dto.quiz.Quiz
import kotlinx.serialization.Serializable

@Serializable
data class QuizResponse(
    val result: Quiz
)

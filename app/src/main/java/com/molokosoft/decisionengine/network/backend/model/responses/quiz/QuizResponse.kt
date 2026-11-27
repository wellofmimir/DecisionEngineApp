package com.molokosoft.decisionengine.network.backend.model.responses.quiz

import com.molokosoft.decisionengine.newdecisionscreen.viewmodel.model.Quiz
import kotlinx.serialization.Serializable

@Serializable
data class QuizResponse(
    val result: Quiz
)

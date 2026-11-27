package com.molokosoft.decisionengine.newdecisionscreen.viewmodel.model

import kotlinx.serialization.Serializable

@Serializable
data class QuizCriterion(
    val id: String,
    val name: String,
    val description: String,
    val initialWeight: Int = 5
)

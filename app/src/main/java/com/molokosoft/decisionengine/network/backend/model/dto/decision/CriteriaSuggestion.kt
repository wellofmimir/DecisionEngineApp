package com.molokosoft.decisionengine.network.backend.model.dto.decision

import kotlinx.serialization.Serializable

@Serializable
data class CriterionSuggestion(
    val name: String,
    val description: String
)


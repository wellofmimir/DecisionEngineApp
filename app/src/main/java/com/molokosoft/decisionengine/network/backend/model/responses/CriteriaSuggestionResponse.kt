package com.molokosoft.decisionengine.network.backend.model.responses

import kotlinx.serialization.Serializable
import com.molokosoft.decisionengine.network.backend.model.dto.CriterionSuggestion

@Serializable
data class CriteriaSuggestionResponse(
    val criteria: List<CriterionSuggestion>
)
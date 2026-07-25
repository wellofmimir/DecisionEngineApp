package com.molokosoft.decisionengine.network.backend.model.responses.decision

import com.molokosoft.decisionengine.network.backend.model.dto.decision.CriterionSuggestion
import kotlinx.serialization.Serializable

@Serializable
data class CriteriaSuggestionResponse(
    val criteria: List<CriterionSuggestion>
)
package com.molokosoft.decisionengine.newdecisionscreen.viewmodel.model

import com.molokosoft.decisionengine.network.backend.model.dto.DecisionAnalysisResult
import com.molokosoft.decisionengine.repositories.model.OptionAnalysis
import com.molokosoft.decisionengine.network.backend.model.dto.CriterionSuggestion
data class DecisionDraft(
    val title: String = "",
    val yesOrNoDecision: Boolean = false,
    val options: List<Option> = emptyList(),
    val criteria: List<Criterion> = emptyList(),
    val criteriaSuggestions: List<CriterionSuggestion> = emptyList(),
    val optionAnalyses: List<OptionAnalysis> = emptyList(),
    val decisionAnalysisResult: DecisionAnalysisResult? = null
)

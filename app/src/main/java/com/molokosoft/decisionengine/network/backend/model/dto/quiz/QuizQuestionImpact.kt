package com.molokosoft.decisionengine.network.backend.model.dto.quiz

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SliderQuestionCriterion(
    val criterionId: String,
    val impactRules: List<SliderImpactRule>
)

@Serializable
data class SliderImpactRule(
    val condition: SliderImpactCondition,
    val delta: Int
)

@Serializable
data class SliderImpactCondition(
    val min: Int,
    val max: Int
)

@Serializable
data class YesNoQuestionCriterion(
    val criterionId: String,
    val impactRules: List<YesNoImpactRule>
)

@Serializable
data class YesNoImpactRule(
    val answer: YesNoAnswer,
    val delta: Int
)

@Serializable
enum class YesNoAnswer {
    @SerialName("yes")
    YES,

    @SerialName("no")
    NO
}

@Serializable
data class MultipleChoiceQuestionCriterion(
    val criterionId: String,
    val impactRules: List<MultipleChoiceImpactRule>
)

@Serializable
data class MultipleChoiceImpactRule(
    val answer: String,
    val delta: Int
)

@Serializable
data class ArrangeQuestionCriterion(
    val criterionId: String,
    val impactRules: List<ArrangeImpactRule>
)

@Serializable
data class ArrangeImpactRule(
    val rank: Int,
    val delta: Int
)

@Serializable
data class TextInputQuestionCriterion(
    val criterionId: String,
    val impactRules: TextInputImpactRules
)

@Serializable
data class TextInputImpactRules(
    val evaluationMode: TextEvaluationMode,
    val positiveSignals: List<String>,
    val negativeSignals: List<String>,
    val allowedDelta: AllowedDelta
)

@Serializable
enum class TextEvaluationMode {
    @SerialName("AI_EVALUATED")
    AI_EVALUATED
}

@Serializable
data class AllowedDelta(
    val min: Int,
    val max: Int
)

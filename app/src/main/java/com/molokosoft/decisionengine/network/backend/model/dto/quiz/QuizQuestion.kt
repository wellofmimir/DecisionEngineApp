package com.molokosoft.decisionengine.network.backend.model.dto.quiz

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class QuizQuestion {
    abstract val id: String
    abstract val title: String
    abstract val question: String
}

@Serializable
@SerialName("SLIDER")
data class SliderQuizQuestion(
    override val id: String,
    override val title: String,
    override val question: String,
    val criteria: List<SliderQuestionCriterion>,
    val config: SliderQuestionConfig
) : QuizQuestion()

@Serializable
@SerialName("YES_NO")
data class YesNoQuizQuestion(
    override val id: String,
    override val title: String,
    override val question: String,
    val criteria: List<YesNoQuestionCriterion>,
    val config: YesNoQuestionConfig
) : QuizQuestion()

@Serializable
@SerialName("MULTIPLE_CHOICE")
data class MultipleChoiceQuizQuestion(
    override val id: String,
    override val title: String,
    override val question: String,
    val criteria: List<MultipleChoiceQuestionCriterion>,
    val config: MultipleChoiceQuestionConfig
) : QuizQuestion()

@Serializable
@SerialName("ARRANGE")
data class ArrangeQuizQuestion(
    override val id: String,
    override val title: String,
    override val question: String,
    val criteria: List<ArrangeQuestionCriterion>,
    val config: ArrangeQuestionConfig
) : QuizQuestion()

@Serializable
@SerialName("TEXT_INPUT")
data class TextInputQuizQuestion(
    override val id: String,
    override val title: String,
    override val question: String,
    val criteria: List<TextInputQuestionCriterion>,
    val config: TextInputQuestionConfig
) : QuizQuestion()

package com.molokosoft.decisionengine.network.backend.model.dto.quiz

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SliderQuestionConfig(
    val min: Int,
    val max: Int,
    val step: Int,
    val minLabel: String,
    val maxLabel: String
)

@Serializable
data class YesNoQuestionConfig(
    val yesLabel: String,
    val noLabel: String
)

@Serializable
data class MultipleChoiceQuestionConfig(
    val selectionMode: SelectionMode,
    val answers: List<MultipleChoiceAnswer>
)

@Serializable
data class MultipleChoiceAnswer(
    val id: String,
    val label: String
)

@Serializable
enum class SelectionMode {
    @SerialName("SINGLE")
    SINGLE
}

@Serializable
data class ArrangeQuestionConfig(
    val rankingMode: RankingMode,
    val items: List<ArrangeItem>
)

@Serializable
data class ArrangeItem(
    val id: String,
    val label: String,
    val criterionId: String
)

@Serializable
enum class RankingMode {
    @SerialName("ALL_ITEMS")
    ALL_ITEMS
}

@Serializable
data class TextInputQuestionConfig(
    val placeholder: String,
    val minLength: Int,
    val maxLength: Int
)

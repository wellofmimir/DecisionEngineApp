package com.molokosoft.decisionengine.network.backend.model.dto.dailyarticle

import kotlinx.serialization.Serializable

@Serializable
data class DailyArticle(
    val title: String,
    val topic: String,
    val readingTimeMinutes: Int,
    val summary: String,
    val content: String,
    val takeAwayPoints: List<String>
)

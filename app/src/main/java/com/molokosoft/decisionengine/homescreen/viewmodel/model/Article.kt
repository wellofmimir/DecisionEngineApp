package com.molokosoft.decisionengine.homescreen.viewmodel.model

data class Article(
    val title: String = "",
    val topic: String = "",
    val readingTimeMinutes: Int = 1,
    val summary: String = "",
    val content: String = "",
    val takeAwayPoints: List<String> = emptyList()
)

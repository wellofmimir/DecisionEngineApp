package com.molokosoft.decisionengine.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "DailyArticle")
data class DailyArticleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String = "",
    val summary: String = "",
    val content: String = "",
)
package com.molokosoft.decisionengine.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Options")
data class OptionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val decisionId: Long,
    val name: String,
    val reversibility: Int = 1,
    val confidence: Int
)

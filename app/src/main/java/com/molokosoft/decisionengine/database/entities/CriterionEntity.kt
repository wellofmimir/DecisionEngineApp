package com.molokosoft.decisionengine.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Criteria")
data class CriterionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val optionId: Long,
    val name: String,
    val importance: Int,
    val score: Int,
    val contribution: Double,
    val percentage: Double
)

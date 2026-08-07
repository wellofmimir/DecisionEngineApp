package com.molokosoft.decisionengine.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Decisions")
data class DecisionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val category: String,
    val summary: String,
    val recommendedOption: String,
    val whyItStandsOut: String,
    val reversibility: String,
    val blindSpots: String,
    val roadmapToSuccess: String,
    val conclusion: String,
    val createdAt: Long
)
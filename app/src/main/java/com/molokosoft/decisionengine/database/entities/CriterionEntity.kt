package com.molokosoft.decisionengine.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.molokosoft.decisionengine.newdecisionscreen.viewmodel.model.Criterion

@Entity(tableName = "Criteria")
data class CriterionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val optionId: Long,
    val name: String,
    val importance: Int,
    val score: Int
)

fun CriterionEntity.toDomain() =
    Criterion(
        name = name,
        importance = importance,
        score = score
    )

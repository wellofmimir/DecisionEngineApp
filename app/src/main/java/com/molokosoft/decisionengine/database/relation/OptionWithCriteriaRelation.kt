package com.molokosoft.decisionengine.database.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.molokosoft.decisionengine.database.entities.CriterionEntity
import com.molokosoft.decisionengine.database.entities.OptionEntity

data class OptionWithCriteriaRelation(

    @Embedded
    val option: OptionEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "optionId"
    )

    val criteria: List<CriterionEntity>
)

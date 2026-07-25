package com.molokosoft.decisionengine.database.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.molokosoft.decisionengine.database.entities.DecisionEntity
import com.molokosoft.decisionengine.database.entities.OptionEntity

data class DecisionCompleteRelation(

    @Embedded
    val decision: DecisionEntity,

    @Relation(
        entity = OptionEntity::class,
        parentColumn = "id",
        entityColumn = "decisionId"
    )

    val options: List<OptionWithCriteriaRelation>
)

package com.molokosoft.decisionengine.database.dao

import android.location.Criteria
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.molokosoft.decisionengine.database.entities.CriterionEntity
import kotlinx.coroutines.flow.Flow

import com.molokosoft.decisionengine.database.entities.DecisionEntity
import com.molokosoft.decisionengine.database.entities.OptionEntity
import com.molokosoft.decisionengine.database.relation.DecisionCompleteRelation

@Dao
interface DecisionDao {
    @Insert
    suspend fun insertDecision(decisionEntity: DecisionEntity): Long

    @Insert
    suspend fun insertOption(optionEntity: OptionEntity): Long

    @Insert
    suspend fun insertCriterion(criterionEntity: CriterionEntity)

    @Insert
    suspend fun insertCriteria(criteria: List<CriterionEntity>)

    @Transaction
    @Query("SELECT * FROM Decisions ORDER BY createdAt DESC")
    fun getAll(): Flow<List<DecisionCompleteRelation>>
}
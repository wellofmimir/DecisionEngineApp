package com.molokosoft.decisionengine.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.molokosoft.decisionengine.database.entities.DailyArticleEntity
import com.molokosoft.decisionengine.database.relation.DecisionCompleteRelation
import kotlinx.coroutines.flow.Flow

@Dao
interface DailyArticleDao {

    @Insert
    suspend fun insertArticle(articleEntity: DailyArticleEntity)

    @Transaction
    @Query("SELECT * FROM DailyArticle ORDER BY id DESC")
    fun getLatestArticle(): DailyArticleEntity
}
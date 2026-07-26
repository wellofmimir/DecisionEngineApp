package com.molokosoft.decisionengine.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.molokosoft.decisionengine.database.dao.DailyArticleDao

import com.molokosoft.decisionengine.database.entities.DecisionEntity
import com.molokosoft.decisionengine.database.dao.DecisionDao
import com.molokosoft.decisionengine.database.entities.CriterionEntity
import com.molokosoft.decisionengine.database.entities.DailyArticleEntity
import com.molokosoft.decisionengine.database.entities.OptionEntity

@Database(
    entities = [
        DecisionEntity::class,
        OptionEntity::class,
        CriterionEntity::class,
        DailyArticleEntity::class
    ],
    version = 1
)

abstract class DecisionDatabase : RoomDatabase() {
    abstract fun decisionDao(): DecisionDao
    abstract fun dailyArticleDao(): DailyArticleDao
}
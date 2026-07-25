package com.molokosoft.decisionengine.database

import android.content.Context

object DatabaseProvider {

    @Volatile
    private var INSTANCE: DecisionDatabase? = null

    fun getDatabase(context: Context): DecisionDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = androidx.room.Room.databaseBuilder(
                context.applicationContext,
                DecisionDatabase::class.java,
                "decisions_database"
            ).build()

            INSTANCE = instance
            instance
        }
    }
}
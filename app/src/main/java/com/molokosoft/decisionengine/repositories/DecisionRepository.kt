package com.molokosoft.decisionengine.repositories

import android.content.Context
import androidx.room.withTransaction
import com.molokosoft.decisionengine.database.DatabaseProvider
import com.molokosoft.decisionengine.database.entities.CriterionEntity
import com.molokosoft.decisionengine.database.entities.DecisionEntity
import com.molokosoft.decisionengine.database.entities.OptionEntity
import com.molokosoft.decisionengine.newdecisionscreen.viewmodel.model.DecisionDraft

class DecisionRepository(
    context: Context
) {
    private val database =
        DatabaseProvider.getDatabase(context)

    private val dao =
            database.decisionDao()

    val decisionHistory =
        dao.getAll()

    suspend fun insertDecision(draft: DecisionDraft) {
        database.withTransaction {
            val decisionId = dao.insertDecision(
                DecisionEntity(
                    title = draft.title,
                    category = draft.decisionAnalysisResult?.category ?: "GENERIC",
                    createdAt = System.currentTimeMillis()
                )
            )

            var optionId: Long = 0

            draft.optionAnalyses.forEach { option ->
                optionId = dao.insertOption(
                    OptionEntity(
                        decisionId = decisionId,
                        name = option.name,
                        reversibility = option.reversibility,
                        confidence = option.weightedScoreAsPercentage()
                    )
                )

                draft.options.forEach { subOption ->
                    subOption.criteria.forEach { criterion ->
                        dao.insertCriterion(
                            CriterionEntity(
                                optionId = optionId,
                                name = criterion.name,
                                importance = criterion.importance,
                                score = criterion.score
                            )
                        )
                    }
                }
            }
        }
    }
}
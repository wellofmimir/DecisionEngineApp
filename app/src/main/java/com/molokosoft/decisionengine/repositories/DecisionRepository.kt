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
                    category = draft.decisionAnalysisResult?.category.orEmpty(),
                    summary = draft.decisionAnalysisResult?.summary.orEmpty(),
                    recommendedOption = draft.decisionAnalysisResult?.recommendedOption.orEmpty(),
                    whyItStandsOut = draft.decisionAnalysisResult?.whyItStandsOut.orEmpty(),
                    reversibility = draft.decisionAnalysisResult?.reversibility.orEmpty(),
                    blindSpots = draft.decisionAnalysisResult?.blindSpots.orEmpty(),
                    roadmapToSuccess = draft.decisionAnalysisResult?.roadmapToSuccess.orEmpty(),
                    conclusion = draft.decisionAnalysisResult?.conclusion.orEmpty(),
                    createdAt = System.currentTimeMillis()
                )
            )

            draft.optionAnalyses.zip(draft.options).forEach { (analysis, option) ->
                val optionId = dao.insertOption(
                    OptionEntity(
                        decisionId = decisionId,
                        name = analysis.name,
                        reversibility = analysis.reversibility,
                        confidence = analysis.weightedScoreAsPercentage()
                    )
                )

                analysis.analyses.forEach { criterionAnalysis ->
                    dao.insertCriterion(
                        CriterionEntity(
                            optionId = optionId,
                            name = criterionAnalysis.name,
                            importance = criterionAnalysis.importance,
                            score = criterionAnalysis.score,
                            contribution = criterionAnalysis.contribution,
                            percentage = criterionAnalysis.percentage
                        )
                    )
                }
            }
        }
    }
}
package com.molokosoft.decisionengine.repositories

import com.molokosoft.decisionengine.network.backend.model.dto.decision.DecisionAnalysisResult
import com.molokosoft.decisionengine.newdecisionscreen.viewmodel.model.Option
import com.molokosoft.decisionengine.repositories.model.CriterionAnalysis
import com.molokosoft.decisionengine.repositories.model.OptionAnalysis
import com.molokosoft.decisionengine.network.backend.DecisionEngineClient
import com.molokosoft.decisionengine.network.backend.model.dto.decision.DecisionOption
import com.molokosoft.decisionengine.network.backend.model.requests.DecisionAnalysisRequest
import com.molokosoft.decisionengine.network.backend.model.dto.decision.DecisionCriterion
import com.molokosoft.decisionengine.network.backend.model.dto.decision.CriterionSuggestion
import android.util.Log
import kotlinx.coroutines.delay
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import java.io.IOException

fun Option.calculateWeightedScore(): Double {
    val totalImportance = criteria.sumOf { it.importance }

    if (totalImportance == 0)
        return 0.0

    return criteria.sumOf { it.importance * it.score } / totalImportance.toDouble()
}

fun Option.analyze(): OptionAnalysis {
    val weightedScore = calculateWeightedScore()
    val totalContribution = criteria.sumOf { it.importance * it.score }

    val analyses = criteria.map { criterion ->
        val contribution = criterion.importance * criterion.score

        CriterionAnalysis(
            name = criterion.name,
            importance = criterion.importance,
            score = criterion.score,
            contribution = contribution.toDouble(),
            percentage = if (totalContribution > 0) {
                contribution.toDouble() / totalContribution.toDouble() * 100.0
            } else {
                0.0f
            }.toDouble()
        )

    }.sortedByDescending { it.contribution }

    return OptionAnalysis(
        name = name,
        reversibility = reversibility,
        weightedScore = weightedScore,
        analyses = analyses
    )
}

class FactoryAnalysisRepository(private val decisionEngineClient: DecisionEngineClient){
    fun analyzeOptions(options: List<Option>): List<OptionAnalysis> {
        return options.map {
            it.analyze()
        }.sortedByDescending {
            it.weightedScore
        }
    }

    suspend fun getAiAnalysis(analysisResult: List<OptionAnalysis>): DecisionAnalysisResult? {

        val optionList: List<DecisionOption> = analysisResult.map { option ->
            DecisionOption(
                name = option.name,
                overallScore = option.weightedScore,
                reversibility = option.reversibility,
                criteria = option.analyses.map { criterion ->
                    DecisionCriterion(
                        name = criterion.name,
                        importance = criterion.importance,
                        score = criterion.score
                    )
                }
            )
        }

        val decisionAnalysisRequest = DecisionAnalysisRequest(
            decisionTitle = "Decision Title",
            recommendedOption = "Recommended Option",
            options = optionList
        )

        repeat(3) { attempt ->
            try {
                val response = decisionEngineClient.analyze(decisionAnalysisRequest)
                return response?.result

            } catch (e: ClientRequestException) {
                val status = e.response.status.value

                Log.e("DecisionEngine", "Client error $status (Attempt ${attempt + 1})", e)

                if (status != 400 || attempt == 2) {
                    return null
                }

            } catch (e: ServerResponseException) {
                Log.e("DecisionEngine", "Server error ${e.response.status.value}", e)
                return null

            } catch (e: IOException) {
                Log.e("DecisionEngine", "Network error", e)

                if (attempt == 2) {
                    return null
                }

            } catch (e: Exception) {
                Log.e("DecisionEngine", "Unexpected error", e)
                return null
            }

            delay(1500)
        }

        return null
    }

    suspend fun getCriteriaSuggestions(decisionTitle: String): List<CriterionSuggestion> {
        val response = decisionEngineClient.getCriteriaSuggestions(decisionTitle)
        return response?.criteria ?: emptyList()
    }
}
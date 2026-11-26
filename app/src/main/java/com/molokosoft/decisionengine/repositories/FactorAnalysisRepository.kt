package com.molokosoft.decisionengine.repositories

import com.molokosoft.decisionengine.network.backend.model.dto.decision.DecisionAnalysisResult
import com.molokosoft.decisionengine.newdecisionscreen.viewmodel.model.Option
import com.molokosoft.decisionengine.repositories.model.CriterionAnalysis
import com.molokosoft.decisionengine.repositories.model.OptionAnalysis
import com.molokosoft.decisionengine.network.backend.DecisionEngineClient
import com.molokosoft.decisionengine.network.backend.model.dto.decision.DecisionOption
import com.molokosoft.decisionengine.network.backend.model.requests.decision.DecisionAnalysisRequest
import com.molokosoft.decisionengine.network.backend.model.dto.decision.DecisionCriterion
import com.molokosoft.decisionengine.network.backend.model.dto.decision.CriterionSuggestion
import com.molokosoft.decisionengine.network.backend.model.dto.decision.SafetyClassification

import android.util.Log
import com.molokosoft.decisionengine.network.backend.model.dto.security.dto.PromptReconnaissanceResult
import com.molokosoft.decisionengine.network.backend.model.dto.security.requests.PromptReconnaissanceRequest
import com.molokosoft.decisionengine.network.backend.model.requests.decision.SafetyClassificationRequest
import com.molokosoft.decisionengine.network.backend.model.requests.quiz.QuizDecisionRequest
import com.molokosoft.decisionengine.network.backend.model.requests.quiz.QuizGenerationRequest
import com.molokosoft.decisionengine.network.backend.model.responses.quiz.QuizResponse
import kotlinx.coroutines.delay
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import java.io.IOException
import java.util.UUID

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

sealed interface AiAnalysisResult {
    data class Success(val result: DecisionAnalysisResult) : AiAnalysisResult
    data class Error(val message: String) : AiAnalysisResult
}

class FactorAnalysisRepository(
    private val decisionEngineClient: DecisionEngineClient
){
    fun analyzeOptions(options: List<Option>): List<OptionAnalysis> {
        return options.map {
            it.analyze()
        }.sortedByDescending {
            it.weightedScore
        }
    }

    suspend fun getAiAnalysis(analysisResult: List<OptionAnalysis>): AiAnalysisResult {

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

        for (attempt in 1..3) {
            try {
                Log.e(
                    "DecisionEngine",
                    "Start AI-Analysis (Attempt $attempt)"
                )

                val response =
                    decisionEngineClient.analyze(decisionAnalysisRequest)

                if (response?.result != null)
                    return AiAnalysisResult.Success(response.result)

                if (response?.errorMessage != null)
                    return AiAnalysisResult.Error(response.errorMessage)

                Log.e(
                    "DecisionEngine",
                    "Empty response (Attempt $attempt)"
                )

            } catch (e: ClientRequestException) {
                val status = e.response.status.value

                Log.e(
                    "DecisionEngine",
                    "Client error $status (Attempt $attempt)",
                    e
                )

                if (attempt == 3)
                    return AiAnalysisResult.Error("Our server has a tiny problem.\nPlease try again later.")

            } catch (e: ServerResponseException) {
                Log.e(
                    "DecisionEngine",
                    "Server error ${e.response.status.value} (Attempt $attempt)",
                    e
                )

                // Bei 5xx erneut versuchen
                if (attempt == 3)
                    return AiAnalysisResult.Error("Our server has a tiny problem.\nPlease try again later.")

            } catch (e: IOException) {
                Log.e(
                    "DecisionEngine",
                    "Network error (Attempt $attempt)",
                    e
                )

                if (attempt == 3)
                    return AiAnalysisResult.Error("Our server has a tiny problem.\nPlease try again later.")

            } catch (e: Exception) {
                Log.e("DecisionEngine", "Unexpected error (Attempt $attempt)", e)
                return AiAnalysisResult.Error("Our server has a tiny problem.\nPlease try again later.")
            }

            delay(1500)
        }

        return AiAnalysisResult.Error("Our server has a tiny problem.\nPlease try again later.")
    }

    suspend fun getCriteriaSuggestions(decisionTitle: String): List<CriterionSuggestion> {
        val response = decisionEngineClient.getCriteriaSuggestions(decisionTitle)
        return response?.criteria ?: emptyList()
    }

    suspend fun generateQuiz(decisionTitle: String): QuizResponse? {
        val request = QuizGenerationRequest(
            decision = QuizDecisionRequest(
                id = "decision_${UUID.randomUUID()}",
                title = decisionTitle
            )
        )

        return decisionEngineClient.generateQuiz(request)
    }

    suspend fun safetyClassification(decisionTitle: String): SafetyClassification {
        val safetyClassificationRequest = SafetyClassificationRequest(
            title = decisionTitle
        )

        val response =
            decisionEngineClient.safetyClassification(safetyClassificationRequest)

        return response?.safetyClassification ?: SafetyClassification(
            classification = "NOT_ALLOWED",
            reason = "FAILURE"
        )
    }

    suspend fun promptReconnaissance(input: String): PromptReconnaissanceResult {
        val promptReconnaissanceRequest =
            PromptReconnaissanceRequest(
                prompt = input
            )

        val response =
            decisionEngineClient.promptReconnaissance(promptReconnaissanceRequest)

        return response?.result ?: PromptReconnaissanceResult(
            isPrompt = true,
            reason = "FAILURE TO READ SERVER."
        )
    }
}

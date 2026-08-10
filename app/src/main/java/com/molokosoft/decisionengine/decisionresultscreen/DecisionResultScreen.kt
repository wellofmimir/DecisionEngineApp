package com.molokosoft.decisionengine.decisionresultscreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.molokosoft.decisionengine.commonuielements.WaitingScreen

import com.molokosoft.decisionengine.decisionresultscreen.screens.RecommendedChoiceScreen
import com.molokosoft.decisionengine.decisionresultscreen.DecisionResultScreen.*
import com.molokosoft.decisionengine.decisionresultscreen.screens.FullDecisionOverviewScreen
import com.molokosoft.decisionengine.decisionresultscreen.screens.ReversibilityAnalysisScreen
import com.molokosoft.decisionengine.decisionresultscreen.screens.ScoreBreakdownScreen
import com.molokosoft.decisionengine.repositories.model.OptionAnalysis
import com.molokosoft.decisionengine.network.backend.model.dto.decision.DecisionAnalysisResult
import com.molokosoft.decisionengine.decisionresultscreen.screens.OptionComparisonScreen

sealed class DecisionResultScreen {
    data object RecommendedDecision : DecisionResultScreen()
    data object ScoreBreakdown : DecisionResultScreen()

    data object OptionComparison : DecisionResultScreen()

    data object ReversibilityAnalysis : DecisionResultScreen()

    data object Summary : DecisionResultScreen()
}

fun DecisionResultScreen.next(): DecisionResultScreen =
    when (this) {
        RecommendedDecision -> ScoreBreakdown
        ScoreBreakdown -> OptionComparison
        OptionComparison -> ReversibilityAnalysis
        ReversibilityAnalysis -> Summary
        Summary -> RecommendedDecision
    }

@Composable
fun DecisionResultScreen(
    modifier: Modifier = Modifier,
    optionAnalyses: List<OptionAnalysis>,
    decisionAnalysisResult: DecisionAnalysisResult?,
    onContinueClicked: () -> Unit
){
    var currentScreen by remember {
        mutableStateOf<DecisionResultScreen>(RecommendedDecision)
    }

    if (decisionAnalysisResult == null) {
        WaitingScreen(
            text = "Analyzing your decision...\n" + "This usually takes just a few seconds."
        )

        return
    }

    when (currentScreen) {
        RecommendedDecision -> RecommendedChoiceScreen(
            modifier = modifier,
            optionAnalyses = optionAnalyses,
            onContinueClicked = {
                currentScreen = currentScreen.next()
            }
        )

        ScoreBreakdown -> ScoreBreakdownScreen(
            modifier = modifier,
            optionAnalyses = optionAnalyses,
            decisionAnalysisResult = decisionAnalysisResult,
            onContinueButtonText = "See Option Breakdown",
            onContinueClicked = {
                currentScreen = currentScreen.next()
            }
        )

        OptionComparison -> OptionComparisonScreen(
            modifier = modifier,
            optionAnalyses = optionAnalyses,
            onContinueClicked = {
                currentScreen = currentScreen.next()
            },
            onBackClicked = {
            },
            onContinueButtonText = "See Reversibility"
        )

        ReversibilityAnalysis -> ReversibilityAnalysisScreen(
            modifier = modifier,
            decisionAnalysisResult = decisionAnalysisResult,
            optionAnalyses = optionAnalyses,
            onContinueClicked = {
                currentScreen = currentScreen.next()
            }
        )

        Summary -> FullDecisionOverviewScreen(
            modifier = modifier,
            decisionAnalysisResult = decisionAnalysisResult,
            onContinueClicked = {
                onContinueClicked()
            }
        )
    }
}
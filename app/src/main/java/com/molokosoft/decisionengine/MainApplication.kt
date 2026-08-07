package com.molokosoft.decisionengine

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.rememberCoroutineScope

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import com.molokosoft.decisionengine.decisionresultscreen.DecisionResultScreen
import com.molokosoft.decisionengine.homescreen.Home
import com.molokosoft.decisionengine.homescreen.navigation.NavigationBar
import com.molokosoft.decisionengine.homescreen.navigation.NavigationItem
import com.molokosoft.decisionengine.newdecisionscreen.EnterDecisionScreen
import com.molokosoft.decisionengine.newdecisionscreen.viewmodel.NewDecisionViewModel
import androidx.compose.runtime.collectAsState
import com.molokosoft.decisionengine.decisionhistoryscreen.DecisionHistoryScreen
import com.molokosoft.decisionengine.decisionhistoryscreen.viewmodel.DecisionHistoryViewModel
import com.molokosoft.decisionengine.homescreen.viewmodel.HomeScreenViewModel
import com.molokosoft.decisionengine.settingsscreen.SettingsScreen
import com.molokosoft.decisionengine.settingsscreen.model.SettingsScreenViewModel
import kotlinx.coroutines.launch

@Composable
fun MainApplication(
    newDecisionViewModel: NewDecisionViewModel,
    decisionHistoryViewModel: DecisionHistoryViewModel,
    homeScreenViewModel: HomeScreenViewModel,
    settingsScreenViewModel: SettingsScreenViewModel,
    showMotivationalQuote: Boolean = false
){
    val decisionDraft by newDecisionViewModel.draft.collectAsState()
    val showNavigationBar by newDecisionViewModel.showBottomBar.collectAsState()
    val scope = rememberCoroutineScope()

    var navigationItem by remember {
        mutableStateOf(
            value =
                if (decisionDraft.optionAnalyses.isEmpty() || showMotivationalQuote)
                    NavigationItem.HOME
                else
                    NavigationItem.SEE_DECISION
        )
    }

    Scaffold(
        modifier = Modifier
            .background(
                color = Color.White
            )
            .fillMaxSize(),
        bottomBar = {
            if (showNavigationBar) {
                NavigationBar(
                    selectedItem = navigationItem,
                    onNavigationSelected = {
                        navigationItem = it
                    }
                )
            }
        }
    ) { innerPadding ->

        when (navigationItem) {
            NavigationItem.HOME -> Home(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(innerPadding),
                onClicked = {
                    navigationItem = NavigationItem.NEW_DECISION
                },
                onViewHistoryRequested = {
                    navigationItem = NavigationItem.HISTORY
                },
                onSettingsHistoryClicked = {
                    navigationItem = NavigationItem.SETTINGS
                },
                onShowOldDecisionClicked = { oldDecision ->
                    scope.launch {
                        newDecisionViewModel.setDraftToOldDecision(oldDecision.decision.id)
                        navigationItem = NavigationItem.SEE_DECISION
                    }
                },
                homeScreenViewModel = homeScreenViewModel,
                showMotivationalQuote = showMotivationalQuote
            )

            NavigationItem.NEW_DECISION -> {
                newDecisionViewModel.resetDraft()

                EnterDecisionScreen(
                    newDecisionViewModel,
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(innerPadding),
                    onBackClicked = {
                        navigationItem = NavigationItem.HOME
                    },
                    onContinueClicked = {
                        navigationItem = NavigationItem.SEE_DECISION
                    }
                )
            }

            NavigationItem.HISTORY -> DecisionHistoryScreen(
                decisionHistoryViewModel = decisionHistoryViewModel,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(innerPadding),
                onSettingsHistoryClicked = {
                    navigationItem = NavigationItem.SETTINGS
                },
                onShowOldDecision = { oldDecision ->
                    scope.launch {
                        newDecisionViewModel.setDraftToOldDecision(oldDecision.decision.id)
                        navigationItem = NavigationItem.SEE_DECISION
                    }
                }
            )

            NavigationItem.SEE_DECISION -> {
                DecisionResultScreen(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(innerPadding),
                    decisionAnalysisResult = decisionDraft.decisionAnalysisResult,
                    optionAnalyses = decisionDraft.optionAnalyses,
                    onContinueClicked = {
                        newDecisionViewModel.showBottomBar()
                        navigationItem = NavigationItem.HOME
                        newDecisionViewModel.resetDraft()
                    }
                )
            }

            NavigationItem.SETTINGS -> {
                SettingsScreen(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(innerPadding),
                    settingsScreenViewModel = settingsScreenViewModel
                )
            }
        }
    }
}
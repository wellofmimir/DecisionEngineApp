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

@Composable
fun MainApplication(
    newDecisionViewModel: NewDecisionViewModel,
    decisionHistoryViewModel: DecisionHistoryViewModel,
    homeScreenViewModel: HomeScreenViewModel,
    settingsScreenViewModel: SettingsScreenViewModel
){
    val decisionDraft by newDecisionViewModel.draft.collectAsState()
    val showNavigationBar by newDecisionViewModel.showBottomBar.collectAsState()

    var navigationItem by remember {
        mutableStateOf(
            value =
                if (decisionDraft.optionAnalyses.isEmpty())
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
                homeScreenViewModel = homeScreenViewModel
            )

            NavigationItem.NEW_DECISION -> {
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
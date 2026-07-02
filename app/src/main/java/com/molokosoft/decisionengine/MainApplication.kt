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

@Composable
fun MainApplication(
    newDecisionViewModel: NewDecisionViewModel,
){
    val decisionDraft by newDecisionViewModel.draft.collectAsState()
    val showNavigationBar by newDecisionViewModel.showBottomBar.collectAsState()

    var navigationItem by remember {
        mutableStateOf(if (decisionDraft.optionAnalyses.isEmpty()) NavigationItem.HOME else NavigationItem.SEE_DECISION )
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
                name = "Patryk",
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(innerPadding)
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

            NavigationItem.HISTORY -> Home(
                name = "Patryk",
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(innerPadding)
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
                    }
                )
            }
        }
    }
}
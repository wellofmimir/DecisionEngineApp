package com.molokosoft.decisionengine

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.molokosoft.decisionengine.commonclasses.ProductTypes
import com.molokosoft.decisionengine.decisionhistoryscreen.DecisionHistoryScreen
import com.molokosoft.decisionengine.decisionhistoryscreen.viewmodel.DecisionHistoryViewModel
import com.molokosoft.decisionengine.homescreen.viewmodel.HomeScreenViewModel
import com.molokosoft.decisionengine.newdecisionscreen.viewmodel.model.ErrorCodes
import com.molokosoft.decisionengine.paywall.PaywallScreen
import com.molokosoft.decisionengine.repositories.BackendRepository
import com.molokosoft.decisionengine.repositories.UserDataRepository
import com.molokosoft.decisionengine.settingsscreen.SettingsScreen
import com.molokosoft.decisionengine.settingsscreen.model.SettingsScreenViewModel
import kotlinx.coroutines.launch

@Composable
fun MainApplication(
    activity: Activity,
    backendRepository: BackendRepository,
    newDecisionViewModel: NewDecisionViewModel,
    decisionHistoryViewModel: DecisionHistoryViewModel,
    homeScreenViewModel: HomeScreenViewModel,
    settingsScreenViewModel: SettingsScreenViewModel,
    userDataRepository: UserDataRepository,
    showMotivationalQuote: Boolean = false,
    onBackendNotAvailable: () -> Unit
){
    val decisionDraft by newDecisionViewModel.draft.collectAsState()
    val subscriptionProducts by newDecisionViewModel.subscriptionProducts.collectAsState()

    val scope =
        rememberCoroutineScope()

    var navigationItem by remember {
        mutableStateOf(
            value =
                if (decisionDraft.optionAnalyses.isEmpty() || showMotivationalQuote)
                    NavigationItem.HOME
                else
                    NavigationItem.SEE_DECISION
        )
    }

    val showBottomBar = when (navigationItem) {
        NavigationItem.HOME -> true
        NavigationItem.NEW_DECISION -> false
        NavigationItem.SETTINGS -> true
        NavigationItem.HISTORY -> true
        NavigationItem.SEE_DECISION -> false
        NavigationItem.PAYWALL -> false
    }

    LaunchedEffect(navigationItem) {
        if (navigationItem == NavigationItem.NEW_DECISION) {
            val isAvailable =
                backendRepository.checkAvailability()

            if (!isAvailable)
                onBackendNotAvailable()
        }
    }

    Scaffold(
        modifier = Modifier
            .background(
                color = Color.White
            )
            .fillMaxSize(),
        bottomBar = {
            if (showBottomBar) {
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
                    newDecisionViewModel.resetDraft()
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
                EnterDecisionScreen(
                    newDecisionViewModel,
                    productInformation = subscriptionProducts,
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(innerPadding),
                    onBackClicked = {
                        navigationItem = NavigationItem.HOME
                    },
                    onContinueClicked = { errorType ->

                        when (errorType) {
                            ErrorCodes.SUCCESS -> {
                                navigationItem = NavigationItem.SEE_DECISION
                            }

                            ErrorCodes.NO_MORE_USAGES -> {
                                newDecisionViewModel.startBillingProcess(
                                    productType = ProductTypes.Usages15,
                                    activity = activity,
                                    apiKey = userDataRepository.apiKey().ifBlank {
                                        null
                                    },
                                    onSuccess = {
                                        newDecisionViewModel.onComparisonCompleted()
                                        navigationItem = NavigationItem.SEE_DECISION
                                    },
                                    onFailure = {
                                        navigationItem = NavigationItem.HOME
                                    }
                                )
                            }
                        }
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
                        newDecisionViewModel.resetDraft()
                        navigationItem = NavigationItem.HOME
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

            NavigationItem.PAYWALL -> {
                PaywallScreen(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(innerPadding),
                    subscriptionProducts = subscriptionProducts,
                    onContinueClicked = { productType, eMail ->

                        newDecisionViewModel.startBillingProcess(
                            productType = productType,
                            activity = activity,
                            apiKey = userDataRepository.apiKey().ifBlank {
                                null
                            },
                            onSuccess = {
                                newDecisionViewModel.onComparisonCompleted()
                            },
                            onFailure = {
                            }
                        )
                    },
                    onBackClicked = {
                        newDecisionViewModel.resetDraft()
                        navigationItem = NavigationItem.HOME
                    },
                    showOnlyOffer = true
                )
            }
        }
    }
}
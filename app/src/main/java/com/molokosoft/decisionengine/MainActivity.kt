package com.molokosoft.decisionengine

import com.molokosoft.decisionengine.newdecisionscreen.EnterDecisionScreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel

import com.molokosoft.decisionengine.theme.DecisionEngineTheme
import com.molokosoft.decisionengine.welcomescreen.WelcomeScreen
import com.molokosoft.decisionengine.AppState.*
import com.molokosoft.decisionengine.network.SharedHttpClient
import com.molokosoft.decisionengine.newdecisionscreen.viewmodel.NewDecisionViewModel
import com.molokosoft.decisionengine.paywall.PaywallScreen
import com.molokosoft.decisionengine.repositories.FactoryAnalysisRepository
import com.molokosoft.decisionengine.network.backend.DecisionEngineClient
import com.molokosoft.decisionengine.repositories.UserDataRepository
import com.molokosoft.decisionengine.billing.BillingManager
import com.molokosoft.decisionengine.decisionhistoryscreen.viewmodel.DecisionHistoryViewModel
import com.molokosoft.decisionengine.preferences.SecurePreferences
import com.molokosoft.decisionengine.repositories.DecisionRepository
import androidx.compose.runtime.LaunchedEffect
import com.molokosoft.decisionengine.homescreen.viewmodel.HomeScreenViewModel
import com.molokosoft.decisionengine.repositories.ArticlesRepository
import com.molokosoft.decisionengine.settingsscreen.SettingsScreen
import com.molokosoft.decisionengine.settingsscreen.model.SettingsScreenViewModel


sealed class AppState {
    data object Welcome : AppState()
    data object Onboarding : AppState()
    data object Paywall : AppState()
    data object MainApp : AppState()
}

fun AppState.next(): AppState =
    when (this) {
        Welcome -> Onboarding
        Onboarding -> Paywall
        Paywall -> MainApp
        MainApp -> MainApp
    }

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val securePreferences = SecurePreferences(applicationContext)
            val decisionRepository = DecisionRepository(applicationContext)
            val decisionEngineClient = DecisionEngineClient(SharedHttpClient.sharedClient, "")
            val userDataRepository = UserDataRepository(decisionEngineClient, securePreferences)

            @Suppress("UNCHECKED_CAST")
            val newDecisionViewModel: NewDecisionViewModel = viewModel(
                factory = object : ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {

                        return NewDecisionViewModel(
                            factorAnalysisRepository = FactoryAnalysisRepository(decisionEngineClient),
                            userDataRepository = UserDataRepository(decisionEngineClient, securePreferences),
                            decisionRepository = decisionRepository,
                            billingManager = BillingManager(applicationContext)
                        ) as T
                    }
                }
            )

            @Suppress("UNCHECKED_CAST")
            val decisionHistoryViewModel: DecisionHistoryViewModel = viewModel(
                factory = object : ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {

                        return DecisionHistoryViewModel(
                            decisionRepository = decisionRepository
                        ) as T
                    }
                }
            )

            @Suppress("UNCHECKED_CAST")
            val homeScreenViewModel: HomeScreenViewModel = viewModel(
                factory = object : ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {

                        val articlesRepository = ArticlesRepository(applicationContext, securePreferences, decisionEngineClient)

                        return HomeScreenViewModel(
                            articlesRepository = articlesRepository,
                            decisionRepository = decisionRepository,
                            userDataRepository = userDataRepository
                        ) as T
                    }
                }
            )

            @Suppress("UNCHECKED_CAST")
            val settingsScreenViewModel: SettingsScreenViewModel = viewModel(
                factory = object : ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        return SettingsScreenViewModel(
                            userDataRepository = userDataRepository
                        ) as T
                    }
                }
            )

            var appState by remember {
                mutableStateOf<AppState>(Welcome)
            }

            LaunchedEffect(Unit) {
                newDecisionViewModel.checkSubscription(
                    onSuccess = {
                        appState = MainApp
                    },
                    onFailure = {
                        appState = MainApp
                    }
                )
            }

            DecisionEngineTheme {
                when (appState) {
                    Welcome -> WelcomeScreen(
                        onContinueClicked = {
                            appState = appState.next()
                        }
                    )

                    Onboarding -> EnterDecisionScreen(
                        viewModel = newDecisionViewModel,
                        onBackClicked = {
                            appState = Welcome
                        },
                        onContinueClicked = {
                            appState = appState.next()
                        }
                    )

                    Paywall -> PaywallScreen(
                        onContinueClicked = { subscriptionType, eMail ->

                            appState = appState.next()
                            return@PaywallScreen

                            newDecisionViewModel.startBillingProcess(
                                subscriptionType = subscriptionType,
                                activity = this,
                                onSuccess = {
                                    appState = appState.next()

                                    if (eMail != null)
                                        newDecisionViewModel.saveEMail(eMail)
                                },
                                onFailure = {

                                }
                            )
                        }
                    )

                    MainApp -> {
                        MainApplication(
                            newDecisionViewModel = newDecisionViewModel,
                            decisionHistoryViewModel = decisionHistoryViewModel,
                            homeScreenViewModel = homeScreenViewModel,
                            settingsScreenViewModel = settingsScreenViewModel
                        )
                    }
                }
            }
        }
    }
}

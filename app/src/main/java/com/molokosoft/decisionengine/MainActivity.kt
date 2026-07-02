package com.molokosoft.decisionengine

import com.molokosoft.decisionengine.newdecisionscreen.EnterDecisionScreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect

import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

import com.molokosoft.decisionengine.theme.DecisionEngineTheme
import com.molokosoft.decisionengine.welcomescreen.WelcomeScreen
import com.molokosoft.decisionengine.AppState.*
import com.molokosoft.decisionengine.network.SharedHttpClient
import com.molokosoft.decisionengine.newdecisionscreen.viewmodel.NewDecisionViewModel
import com.molokosoft.decisionengine.paywall.PaywallScreen
import com.molokosoft.decisionengine.repositories.FactoryAnalysisRepository
import com.molokosoft.decisionengine.network.backend.DecisionEngineClient
import com.molokosoft.decisionengine.repositories.UserDataRepository
import com.molokosoft.decisionengine.smartphone.AppSetIdProvider
import kotlinx.coroutines.launch


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

            val newDecisionViewModel: NewDecisionViewModel = viewModel(
                factory = object : ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {

                        val decisionEngineClient = DecisionEngineClient(SharedHttpClient.sharedClient)

                        return NewDecisionViewModel(
                            factorAnalysisRepository = FactoryAnalysisRepository(decisionEngineClient),
                            userDataRepository = UserDataRepository(decisionEngineClient)
                        ) as T
                    }
                }
            )

            var appState by remember {
                mutableStateOf<AppState>(Welcome)
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
                        onContinueClicked = { eMail ->
                            appState = appState.next()

                            if (eMail != null)
                                newDecisionViewModel.saveEMail(eMail)
                        }
                    )

                    MainApp -> {
                        MainApplication(
                            newDecisionViewModel = newDecisionViewModel
                        )
                    }
                }
            }
        }
    }
}

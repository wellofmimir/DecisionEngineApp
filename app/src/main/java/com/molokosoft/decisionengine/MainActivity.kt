package com.molokosoft.decisionengine

import com.molokosoft.decisionengine.newdecisionscreen.EnterDecisionScreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import androidx.compose.foundation.layout.fillMaxHeight

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
import com.molokosoft.decisionengine.repositories.FactorAnalysisRepository
import com.molokosoft.decisionengine.network.backend.DecisionEngineClient
import com.molokosoft.decisionengine.repositories.UserDataRepository
import com.molokosoft.decisionengine.billing.BillingManager
import com.molokosoft.decisionengine.decisionhistoryscreen.viewmodel.DecisionHistoryViewModel
import com.molokosoft.decisionengine.preferences.SecurePreferences
import com.molokosoft.decisionengine.repositories.DecisionRepository
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.molokosoft.decisionengine.commonuielements.ConnectionErrorScreen
import com.molokosoft.decisionengine.commonuielements.ErrorDialog
import kotlinx.coroutines.async
import java.util.UUID
import com.molokosoft.decisionengine.homescreen.viewmodel.HomeScreenViewModel
import com.molokosoft.decisionengine.repositories.ArticlesRepository
import com.molokosoft.decisionengine.repositories.BackendRepository
import com.molokosoft.decisionengine.repositories.QuoteRepository
import com.molokosoft.decisionengine.settingsscreen.model.SettingsScreenViewModel
import com.molokosoft.decisionengine.welcomescreen.SplashScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed interface AppEvent {
    data class ConnectionError(
        val message: String? = null
    ) : AppEvent
}
sealed class AppState {
    data object Loading: AppState()
    data object Welcome : AppState()
    data object Onboarding : AppState()
    data object Paywall : AppState()
    data object MainApp : AppState()

    data object Splash : AppState()

    data object ConnectionError: AppState()
}

fun AppState.next(): AppState =
    when (this) {
        Loading -> Splash
        Splash -> Welcome
        Welcome -> Onboarding
        Onboarding -> Paywall
        Paywall -> MainApp
        MainApp -> MainApp
        ConnectionError -> Splash
    }

class MainActivity : ComponentActivity() {

    private val _showMotivationalQuote = MutableStateFlow(false)
    val showMotivationalQuote = _showMotivationalQuote.asStateFlow()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleIntent(intent = intent)
        enableEdgeToEdge()
        setContent {
            val securePreferences = remember {
                SecurePreferences(applicationContext).apply {
                    installationId().ifBlank {
                        setInstallationId(UUID.randomUUID().toString())
                    }
                    setApiKey("leckmich")
                }
            }

            val decisionRepository = remember {
                DecisionRepository(applicationContext)
            }

            val decisionEngineClient = remember {
                DecisionEngineClient(SharedHttpClient.sharedClient).apply {
                    setApiKey(securePreferences.apiKey())
                    setInstallationId(securePreferences.installationId())
                }
            }

            val userDataRepository = remember {
                UserDataRepository(
                    decisionEngineClient,
                    securePreferences
                )
            }

            val backendRepository = remember {
                BackendRepository(
                    decisionEngineClient
                )
            }

            @Suppress("UNCHECKED_CAST")
            val newDecisionViewModel: NewDecisionViewModel = viewModel(
                factory = object : ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {

                        return NewDecisionViewModel(
                            factorAnalysisRepository = FactorAnalysisRepository(decisionEngineClient),
                            userDataRepository = userDataRepository,
                            decisionRepository = decisionRepository,
                            billingManager = BillingManager(applicationContext),
                            decisionEngineClient = decisionEngineClient
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
                        val quoteRepository = QuoteRepository(applicationContext, securePreferences, decisionEngineClient)

                        return HomeScreenViewModel(
                            articlesRepository = articlesRepository,
                            decisionRepository = decisionRepository,
                            userDataRepository = userDataRepository,
                            quoteRepository = quoteRepository
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

            val manager =
                getSystemService(NotificationManager::class.java)

            manager.createNotificationChannel(NotificationChannel("motivationalQuote", "motivationalQuote", NotificationManager.IMPORTANCE_HIGH))

            val showQuote by
                showMotivationalQuote.collectAsState()

            val subscriptionProducts by
                newDecisionViewModel.subscriptionProducts.collectAsState()

            var appState by remember {
                mutableStateOf<AppState>(Loading)
            }

            var hasAccess by remember {
                mutableStateOf(false)
            }

            var hasError by remember {
                mutableStateOf(false)
            }

            var errorMessage by remember {
                mutableStateOf("" to "")
            }

            LaunchedEffect(Unit) {
                userDataRepository.apiKey().ifBlank {
                    appState = Welcome
                    return@LaunchedEffect
                }

                newDecisionViewModel.checkAccess(
                    onAccessGranted = {
                        appState = Splash
                        hasAccess = true
                    },
                    onAccessDenied = {
                        hasAccess = false
                        appState = Welcome
                        newDecisionViewModel.startOnboarding()
                    },
                    onReAccess = {
                        appState = Splash
                        hasAccess = true
                    },
                    onError = {
                        hasAccess = false
                        appState = ConnectionError
                    }
                )
            }

            DecisionEngineTheme {
                when (appState) {

                    Loading -> {}

                    Splash -> SplashScreen(
                        onDone = {
                            if (hasAccess) {
                                appState = MainApp
                                return@SplashScreen
                            }
                        }
                    )

                    Welcome -> WelcomeScreen(
                        onContinueClicked = {
                            appState = appState.next()
                        }
                    )

                    Onboarding -> EnterDecisionScreen(
                        newDecisionViewModel = newDecisionViewModel,
                        productInformation = subscriptionProducts,
                        onBackClicked = {
                            appState = Welcome
                        },
                        onContinueClicked = {
                            appState = appState.next()
                        }
                    )

                    Paywall -> PaywallScreen(
                        onContinueClicked = { productType, eMail ->

                            newDecisionViewModel.startBillingProcess(
                                productType = productType,
                                activity = this,
                                apiKey = securePreferences.apiKey().ifBlank {
                                    null
                                },
                                onSuccess = {
                                    appState = appState.next()
                                    newDecisionViewModel.finishOnboarding()

                                    if (eMail != null)
                                        newDecisionViewModel.saveEMail(eMail)
                                },
                                onFailure = {
                                    errorMessage =
                                        "Billing error." to "Billing error. We couldn't process your purchase. Please try again later."

                                    hasError = true
                                }
                            )
                        },
                        onBackClicked = {

                        },
                        subscriptionProducts = subscriptionProducts
                    )

                    MainApp -> {
                        MainApplication(
                            activity = this,
                            backendRepository = backendRepository,
                            newDecisionViewModel = newDecisionViewModel,
                            decisionHistoryViewModel = decisionHistoryViewModel,
                            homeScreenViewModel = homeScreenViewModel,
                            settingsScreenViewModel = settingsScreenViewModel,
                            userDataRepository = userDataRepository,
                            showMotivationalQuote = showQuote,
                            onBackendNotAvailable = {
                                appState = ConnectionError
                            }
                        )
                    }

                    ConnectionError -> {
                        ConnectionErrorScreen(
                            modifier = Modifier
                                .fillMaxHeight(),
                            onAccepted = {
                                appState = MainApp
                            }
                        )
                    }
                }

                if (hasError) {
                    ErrorDialog(
                        errorTitle = errorMessage.first,
                        errorMessage = errorMessage.second,
                        onDismissRequest = {
                            hasError = false
                        },
                        onAcceptRequest = {
                            hasError = false
                        }
                    )
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        if (intent?.getBooleanExtra("showMotivationalQuote", false) == true) {
            _showMotivationalQuote.value = true
            intent.removeExtra("showMotivationalQuote") //event konsumieren
        }
    }
}

package com.molokosoft.decisionengine.paywall

import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.molokosoft.decisionengine.billing.model.SubscriptionProduct

import com.molokosoft.decisionengine.commonuielements.dialogs.ErrorDialog
import com.molokosoft.decisionengine.commonclasses.EMail

import com.molokosoft.decisionengine.paywall.one.FeaturesScreen
import com.molokosoft.decisionengine.paywall.one.EnforceConversionScreen
import com.molokosoft.decisionengine.paywall.one.FreeTrialScreen
import com.molokosoft.decisionengine.commonclasses.ProductTypes
import com.molokosoft.decisionengine.commonuielements.screens.WaitingScreen
import com.molokosoft.decisionengine.newdecisionscreen.viewmodel.NewDecisionViewModel
import com.molokosoft.decisionengine.paywall.common.SubscriptionInformationDialog

sealed class Paywall {
    data object Features : Paywall()
    data object EnforceConversion: Paywall()
    data object OfferTiles : Paywall()
    data object FreeTrial : Paywall()
}

fun Paywall.next(): Paywall =
    when (this) {
        Paywall.Features -> Paywall.EnforceConversion
        Paywall.EnforceConversion -> Paywall.OfferTiles
        Paywall.OfferTiles -> Paywall.FreeTrial
        Paywall.FreeTrial -> Paywall.Features
    }


@Composable
fun PaywallScreen(
    modifier: Modifier = Modifier,
    newDecisionViewModel: NewDecisionViewModel,
    productInformation: List<SubscriptionProduct>,
    onContinueClicked: (subscriptionType: ProductTypes, eMail: EMail?) -> Unit,
    onBackClicked: () -> Unit,
    onRestore: () -> Unit,
    showOnlyOffer: Boolean = false,
    showRestorage: Boolean = false
){
    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) {}

    var currentScreen by remember {
        mutableStateOf(if (showOnlyOffer)
            Paywall.OfferTiles
        else
            Paywall.Features)
    }

    var hasError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("" to "") }

    var showSubscriptionInformation by remember { mutableStateOf(false) }
    var subscriptionType: ProductTypes by remember { mutableStateOf(ProductTypes.Undefined) }

    val showWaitingForAnalysisScreen by newDecisionViewModel.showWaitingForAnalysisScreen.collectAsStateWithLifecycle()
    var price by remember { mutableStateOf("") }
    val products by newDecisionViewModel.subscriptionProducts.collectAsState()

    when (currentScreen) {
        Paywall.Features -> FeaturesScreen(
            modifier = modifier,
            onContinueClicked = {
                currentScreen = Paywall.OfferTiles
            },
            onDeclineClicked = {
                onBackClicked()
            }
        )

        Paywall.EnforceConversion -> EnforceConversionScreen(
            modifier = modifier,
            onContinueClicked = {
                currentScreen = currentScreen.next()
            }
        )

        Paywall.OfferTiles -> com.molokosoft.decisionengine.paywall.two.OfferTilesScreen(
            modifier = modifier,
            productInformation = productInformation,
            onContinueClicked = { chosenSubscription ->

                if (chosenSubscription == ProductTypes.FreeTrial) {
                    currentScreen = currentScreen.next()
                    return@OfferTilesScreen
                }

                if (chosenSubscription == ProductTypes.Usages15) {
                    price = products.find {
                        it.productId == ProductTypes.Usages15.value
                    }?.formattedPrice ?: ""
                } else if (chosenSubscription == ProductTypes.Weekly) {
                    price = products.find {
                        it.productId == ProductTypes.Weekly.value
                    }?.formattedPrice ?: ""
                }

                showSubscriptionInformation = true
                subscriptionType = chosenSubscription
            },
            onBackClicked = onBackClicked,
            onRestore = {
                onRestore()
            },
            onDeclineClicked = {
                onBackClicked()
            },
            showBackButton = showOnlyOffer,
            showRestorageButton = showRestorage
        )

        Paywall.FreeTrial -> FreeTrialScreen(
            modifier = modifier,
            onBackClicked = {
                currentScreen = Paywall.OfferTiles
            },
            onContinueClicked = { eMailAddress ->
                if (!eMailAddress.isBlank()) {
                    if (EMail.tryCreate(eMailAddress) == null) {
                        errorMessage =
                            "Incorrect E-Mail Format" to "Please enter a valid e-mail address."

                        hasError = true
                        return@FreeTrialScreen
                    }
                }

                if (Build.VERSION.SDK_INT >= 33) {
                    notificationPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                }

                onContinueClicked(ProductTypes.FreeTrial, EMail.tryCreate(eMailAddress))
            }
        )
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

        return
    }

    if (showSubscriptionInformation) {
        SubscriptionInformationDialog(
            subscriptionType = subscriptionType,
            price = price,
            onDismissRequest = {
                showSubscriptionInformation = false
            },
            onAcceptedOffer = {
                showSubscriptionInformation = false
                onContinueClicked(subscriptionType, null)
            }
        )

        return
    }

    if (showWaitingForAnalysisScreen) {
        WaitingScreen(
            modifier = modifier
                .fillMaxHeight(),
            texts = listOf(
                "Analyzing your decision...\n" + "This usually takes just a few seconds.",
                "Weighing the important factors...\n" + "This won't take long.",
                "Working through your decision...\n" + "Almost there.",
                "Finding the key factors in your decision...\n" + "Just a moment.",
                "Walking through your input...\n" + "Just a heartbeat and we're done."
            )
        )

        return
    }
}
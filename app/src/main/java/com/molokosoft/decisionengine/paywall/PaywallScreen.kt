package com.molokosoft.decisionengine.paywall

import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.molokosoft.decisionengine.billing.model.SubscriptionProduct

import com.molokosoft.decisionengine.commonuielements.ErrorDialog
import com.molokosoft.decisionengine.commonclasses.EMail

import com.molokosoft.decisionengine.paywall.one.FeaturesScreen
import com.molokosoft.decisionengine.paywall.one.EnforceConversionScreen
import com.molokosoft.decisionengine.paywall.one.FreeTrialScreen
import com.molokosoft.decisionengine.paywall.one.OfferTilesScreen
import com.molokosoft.decisionengine.commonclasses.SubscriptionTypes

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
    subscriptionProducts: List<SubscriptionProduct>,
    onContinueClicked: (subscriptionType: SubscriptionTypes, eMail: EMail?) -> Unit
){
    val notificationPermissionLauncher = rememberLauncherForActivityResult (
        contract = ActivityResultContracts.RequestPermission()
    ) {}

    var currentScreen by remember {
        mutableStateOf<Paywall>(Paywall.Features)
    }

    var hasError by remember { mutableStateOf(false) }

    if (hasError) {
        ErrorDialog(
            errorTitle = "Incorrect E-Mail Format",
            errorMessage = "Please enter a valid e-mail address.",
            onDismissRequest = {
                hasError = false
            }
        )
    }

    when (currentScreen) {
        Paywall.Features -> FeaturesScreen(
            modifier = modifier,
            onContinueClicked = {
                currentScreen = currentScreen.next()
            }
        )

        Paywall.EnforceConversion -> EnforceConversionScreen(
            modifier = modifier,
            onContinueClicked = {
                currentScreen = currentScreen.next()
            }
        )

        Paywall.OfferTiles -> OfferTilesScreen(
            modifier = modifier,
            subscriptionProducts = subscriptionProducts,
            onContinueClicked = { subscriptionType ->
                if (subscriptionType == SubscriptionTypes.FreeTrial) {
                    currentScreen = currentScreen.next()
                } else {
                    if (Build.VERSION.SDK_INT >= 33) {
                        notificationPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                    }

                    onContinueClicked(subscriptionType, null)
                }
            }
        )

        Paywall.FreeTrial -> FreeTrialScreen(
            modifier = modifier,
            onBackClicked = {

            },
            onContinueClicked = { eMailAddress ->
                if (!eMailAddress.isBlank()) {
                    if (EMail.tryCreate(eMailAddress) == null) {
                        hasError = true
                        return@FreeTrialScreen
                    }
                }

                if (Build.VERSION.SDK_INT >= 33) {
                    notificationPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                }

                onContinueClicked(SubscriptionTypes.FreeTrial, EMail.tryCreate(eMailAddress))
            }
        )
    }
}
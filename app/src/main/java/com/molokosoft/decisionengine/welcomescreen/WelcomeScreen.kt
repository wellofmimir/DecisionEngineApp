package com.molokosoft.decisionengine.welcomescreen

import com.molokosoft.decisionengine.welcomescreen.start.StartScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

sealed class Welcome {
    data object StartingScreen : Welcome()
}

@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier,
    onContinueClicked: () -> Unit
){
    var currentScreen by remember {
        mutableStateOf<Welcome>(Welcome.StartingScreen)
    }

    when (currentScreen) {
        Welcome.StartingScreen -> StartScreen(
            modifier = modifier,
            onContinueClicked = {
                onContinueClicked()
            }
        )
    }
}
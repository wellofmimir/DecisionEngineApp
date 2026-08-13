package com.molokosoft.decisionengine.paywall.one

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration

import androidx.compose.ui.text.withStyle
import com.molokosoft.decisionengine.R
import com.molokosoft.decisionengine.theme.DecisionBlue
import com.molokosoft.decisionengine.theme.DecisionBlueLight
import com.molokosoft.decisionengine.theme.LocalAppTypography

@Composable
fun FreeTrialScreen(
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit,
    onContinueClicked: (email: String) -> Unit
){
    val typography = LocalAppTypography.current
    val focusManager = LocalFocusManager.current

    var email by remember { mutableStateOf("") }
    val verticalScroll = rememberScrollState()

    val isAtBottom by remember {
        derivedStateOf {
            verticalScroll.maxValue == 0 || verticalScroll.value >= verticalScroll.maxValue - 1000
        }
    }

    Column(
        modifier = modifier
            .background(
                color = Color.White
            )
            .fillMaxWidth()
            .verticalScroll(verticalScroll),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            modifier = Modifier
                .height(64.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.decisionenginelogonew),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .aspectRatio(1f)
        )

        Spacer(
            modifier = Modifier
                .height(16.dp)
        )

        Text(
            text = "Free Trial",
            textAlign = TextAlign.Center,
            fontSize = typography.titleLarge.fontSize * 1.5f,
            color = Color.Black
        )

        Spacer(
            modifier = Modifier
                .height(8.dp)
        )

        Text(
            text = "We'll send you a reminder",
            textAlign = TextAlign.Center,
            fontSize = typography.titleMedium.fontSize,
            color = Color.Black
        )

        Text(
            text = "before your trial ends.",
            textAlign = TextAlign.Center,
            fontSize = typography.titleMedium.fontSize,
            color = Color.Black
        )

        Spacer(
            modifier = Modifier
                .height(16.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth(0.15f)
                .aspectRatio(1f),
            contentAlignment = Alignment.Center
        ){
            Image(
                painter = painterResource(id = R.drawable.bell_foreground),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            )

            Image(
                painter = painterResource(id = R.drawable.check_foreground),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .fillMaxWidth(0.35f)
            )
        }

        Spacer(
            modifier = Modifier
                .height(8.dp)
        )

        Text(
            text = "How to cancel",
            textAlign = TextAlign.Center,
            fontSize = typography.titleMedium.fontSize,
            color = Color.Black
        )

        Text(
            text = "my subscription?",
            textAlign = TextAlign.Center,
            fontSize = typography.titleMedium.fontSize,
            color = Color.Black
        )

        Spacer(
            modifier = Modifier
                .height(16.dp)
        )

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = buildAnnotatedString {
                    append("1. Open ")

                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append("Google Play")
                    }
                },
                textAlign = TextAlign.Start,
                fontSize = typography.titleMedium.fontSize,
                color = Color.Black
            )

            Text(
                text = "2. Go to your profile",
                textAlign = TextAlign.Start,
                fontSize = typography.titleMedium.fontSize,
                color = Color.Black
            )

            Text(
                text = buildAnnotatedString {
                    append("3. Open ")

                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append("Payments & Subscriptions")
                    }
                },
                textAlign = TextAlign.Start,
                fontSize = typography.titleMedium.fontSize,
                color = Color.Black
            )

            Text(
                text = "4. Select this app",
                textAlign = TextAlign.Start,
                fontSize = typography.titleMedium.fontSize,
                color = Color.Black
            )

            Text(
                text = buildAnnotatedString {
                    append("5. Tap ")

                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append("Cancel subscription")
                    }
                },
                textAlign = TextAlign.Start,
                fontSize = typography.titleMedium.fontSize,
                color = Color.Black
            )
        }

        Spacer(
            modifier = Modifier
                .height(16.dp)
        )

        Text(
            text = "Remind me via eMail:",
            textAlign = TextAlign.Start,
            fontSize = typography.titleMedium.fontSize,
            color = Color.Black
        )

        Spacer(
            modifier = Modifier
                .height(8.dp)
        )

        OutlinedTextField(
            placeholder = {
                Text(
                    text = "example@example.com",
                    textAlign = TextAlign.Center,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Thin,
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            },
            value = email,
            onValueChange = {
                email = it
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
            textStyle = LocalTextStyle.current.copy(
                textAlign = TextAlign.Center
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = DecisionBlueLight,
                unfocusedContainerColor = DecisionBlueLight,

                focusedBorderColor = DecisionBlueLight,
                unfocusedBorderColor = DecisionBlueLight,

                cursorColor = Color.Black,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(12.dp)
                )
                .border(
                    width = 1.dp,
                    color = DecisionBlueLight,
                    shape = RoundedCornerShape(12.dp)
                )
        )

        Spacer(
            modifier = Modifier
                .height(16.dp)
        )

        AnimatedVisibility(
            visible = isAtBottom,
            modifier = Modifier
                .height(64.dp)
                .border(
                    width = 1.dp,
                    color = DecisionBlue,
                    shape = RoundedCornerShape(64.dp)
                )
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(64.dp)
                )
                .background(
                    color = DecisionBlue,
                    shape = RoundedCornerShape(64.dp)
                )
        ) {
            Box(
                modifier = Modifier
                    .height(64.dp)
                    .fillMaxWidth(0.75f)
                    .border(
                        width = 1.dp,
                        color = DecisionBlue,
                        shape = RoundedCornerShape(64.dp)
                    )
                    .background(
                        color = DecisionBlue,
                        shape = RoundedCornerShape(64.dp)
                    )
                    .clickable {
                        onContinueClicked(email)
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "See Result",
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
            }
        }

        Spacer(
            modifier = Modifier
                .height(16.dp)
        )

        Text(
            text = "Back",
            textAlign = TextAlign.Center,
            color = Color.Black,
            fontSize = typography.titleSmall.fontSize,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier
                .clickable() {
                    onBackClicked()
                }
        )

        Spacer(
            modifier = Modifier
                .height(16.dp)
        )
    }
}
package com.molokosoft.decisionengine.welcomescreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.molokosoft.decisionengine.R
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import com.molokosoft.decisionengine.theme.DecisionBlue
import com.molokosoft.decisionengine.theme.LocalAppTypography
import kotlinx.coroutines.delay


@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier,
    onContinueClicked: () -> Unit
){
    var showLogo by remember { mutableStateOf(false) }
    var shiftLogo by remember { mutableStateOf(false) }
    var showWelcome by remember { mutableStateOf(false) }
    var showButton by remember { mutableStateOf(false) }

    val welcomeAlpha by animateFloatAsState(
        if (showWelcome) 1f else 0f,
        tween(3000),
        label = ""
    )

    val buttonAlpha by animateFloatAsState(
        if (showButton) 1f else 0f,
        tween(2000),
        label = ""
    )

    val logoHeight by animateFloatAsState(
        (if (shiftLogo) 64 else 256).toFloat(),
        tween(3000),
        label = ""
    )

    val logoAlpha by animateFloatAsState(
        if (showLogo) 1f else 0f,
        tween(2000),
        label = ""
    )

    LaunchedEffect(Unit) {
        delay(250)
        showLogo = true

        delay(1000)
        shiftLogo = true

        delay(1000)
        showWelcome = true

        delay(1000)
        showButton = true
    }

    val typography = LocalAppTypography.current

    Column(
        modifier = modifier
            .background(
                color = Color.White
            )
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            modifier = Modifier
                .height(logoHeight.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.decisionenginelogonew),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .aspectRatio(1f)
                .alpha(logoAlpha)
        )

        Spacer(
            modifier = Modifier
                .height(16.dp)
        )

        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontSize = typography.titleLarge.fontSize * 1.25f,
                        color = Color.Black
                    )
                ) {
                    append("Welcome to\n")
                }

                withStyle(
                    style = SpanStyle(
                        fontSize = typography.titleLarge.fontSize * 1.5f,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    )
                ) {
                    append("Decision Engine")
                }
            },
            textAlign = TextAlign.Center,
            lineHeight = 40.sp,
            modifier = Modifier
                .alpha(welcomeAlpha)
        )

        Spacer(
            modifier = Modifier
                .height(16.dp)
        )

        Text(
            text = "Your second brain for",
            textAlign = TextAlign.Center,
            fontSize = typography.titleMedium.fontSize,
            color = Color.Black,
            modifier = Modifier
                .alpha(welcomeAlpha)
        )

        Text(
            text = "important decisions.",
            textAlign = TextAlign.Center,
            fontSize = typography.titleMedium.fontSize,
            color = Color.Black,
            modifier = Modifier
                .alpha(welcomeAlpha)
        )

        Spacer(
            modifier = Modifier
                .height(16.dp)
        )

        Spacer(
            modifier = Modifier
                .weight(1f)
        )

        Box(
            modifier = Modifier
                .alpha(buttonAlpha)
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

                .clickable() {
                    onContinueClicked()
                },
            contentAlignment = Alignment.Center
        ){
            Text(
                text = "Get Started",
                textAlign = TextAlign.Center,
                color = Color.White
            )
        }

        Spacer(
            modifier = Modifier
                .height(16.dp)
        )

        Text( //leerer Text, damit der Abstand des Buttons gleich ist wie bei anderen Screens
            text = "",
            textAlign = TextAlign.Center,
            color = Color.Black,
            fontSize = typography.titleSmall.fontSize,
            textDecoration = TextDecoration.Underline,
        )

        Spacer(
            modifier = Modifier
                .height(16.dp)
        )
    }
}
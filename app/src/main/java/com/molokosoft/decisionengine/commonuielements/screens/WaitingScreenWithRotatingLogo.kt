package com.molokosoft.decisionengine.commonuielements.screens

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.molokosoft.decisionengine.R
import com.molokosoft.decisionengine.theme.LocalAppTypography

private const val MESSAGE_DURATION_MS = 3500

@Composable
fun WaitingScreenWithRotatingLogo(
    texts: List<String>,
    modifier: Modifier = Modifier
) {
    require(texts.isNotEmpty()) {
        "WaitingScreenWithRotatingLogo requires at least one message."
    }

    val typography =
        LocalAppTypography.current

    val infiniteTransition = rememberInfiniteTransition(label = "logoWaitingScreen")

    val logoRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 6000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "logoRotation"
    )

    val messageProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = texts.size.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = texts.size * MESSAGE_DURATION_MS,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "waitingMessage"
    )
    val messageIndex = messageProgress.toInt() % texts.size

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(
                modifier = Modifier
                    .weight(1f)
            )

            Image(
                painter = painterResource(id = R.drawable.decisionenginelogonew),
                contentDescription = "Rotating DecisionEngine logo",
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .aspectRatio(1f)
                    .rotate(logoRotation)
            )

            Spacer(
                modifier = Modifier
                    .height(32.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(96.dp),
                contentAlignment = Alignment.Center
            ) {
                Crossfade(
                    targetState = messageIndex,
                    label = "waitingMessage"
                ) { index ->
                    Text(
                        text = texts[index],
                        modifier = Modifier
                            .fillMaxWidth(),
                        fontSize = typography.titleMedium.fontSize,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        lineHeight = 40.sp
                    )
                }
            }

            Spacer(
                modifier = Modifier
                    .weight(1f)
            )
        }
    }
}

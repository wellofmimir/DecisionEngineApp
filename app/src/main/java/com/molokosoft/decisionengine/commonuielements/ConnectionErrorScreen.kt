package com.molokosoft.decisionengine.commonuielements

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.molokosoft.decisionengine.R
import com.molokosoft.decisionengine.newdecisionscreen.screens.NotAllowedRequestsCard
import com.molokosoft.decisionengine.newdecisionscreen.screens.SuicidePreventionHotline
import com.molokosoft.decisionengine.newdecisionscreen.screens.WeCareAboutYouCard
import com.molokosoft.decisionengine.theme.LocalAppTypography

@Composable
fun ConnectionErrorScreen(
    modifier: Modifier = Modifier,
    onAccepted: () -> Unit
) {
    val typography = LocalAppTypography.current
    val verticalScroll = rememberScrollState()

    Column(
        modifier = modifier
            .verticalScroll(verticalScroll)
            .background(
                color = Color.White
            )
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            modifier = Modifier
                .height(64.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.connectionerror),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .aspectRatio(1f)
        )

        Text(
            text = "Connection Error.\nSomething went wrong.",
            textAlign = TextAlign.Center,
            fontSize = typography.titleMedium.fontSize,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        )

        Spacer(
            modifier = Modifier
                .height(16.dp)
        )

        Text(
            text = "A connection error has occurred.\nPlease try again in a moment.\nIf the problem persists,\nplease contact us.",
            textAlign = TextAlign.Center,
            fontSize = typography.titleSmall.fontSize,
            color = Color.Black
        )

        Spacer(
            modifier = Modifier
                .height(16.dp)
        )

        Spacer(
            modifier = Modifier
                .weight(1f)
        )

        Spacer(
            modifier = Modifier
                .height(16.dp)
        )

        Text(
            text = "Okay",
            textAlign = TextAlign.Center,
            color = Color.Black,
            fontSize = typography.titleSmall.fontSize,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier
                .clickable {
                    onAccepted()
                }
        )

        Spacer(
            modifier = Modifier
                .height(16.dp)
        )
    }
}
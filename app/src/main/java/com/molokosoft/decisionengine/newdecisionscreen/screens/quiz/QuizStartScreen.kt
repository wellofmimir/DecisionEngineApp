package com.molokosoft.decisionengine.newdecisionscreen.screens.quiz

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.molokosoft.decisionengine.R
import com.molokosoft.decisionengine.theme.DecisionBlue
import com.molokosoft.decisionengine.theme.LocalAppTypography

@Composable
fun QuizStartScreen(
    onStartClicked: () -> Unit,
    modifier: Modifier = Modifier,
    title: String = "Your personal quiz is ready",
    subtitle: String = "Answer a few questions to find out what matters most in your decision."
) {
    val typography =
        LocalAppTypography.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White),
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
                .height(24.dp)
        )

        Text(
            text = title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            textAlign = TextAlign.Center,
            fontSize = typography.titleLarge.fontSize,
            lineHeight = typography.titleLarge.fontSize * 1.2f,
            color = Color.Black
        )

        Spacer(
            modifier = Modifier
                .height(16.dp)
        )

        Text(
            text = subtitle,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            textAlign = TextAlign.Center,
            fontSize = typography.bodyLarge.fontSize,
            color = Color.Black
        )

        Spacer(
            modifier = Modifier
                .weight(1f)
        )

        Spacer(
            modifier = Modifier
                .height(16.dp)
        )

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
                    onStartClicked()
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Start",
                textAlign = TextAlign.Center,
                color = Color.White
            )
        }

        Spacer(
            modifier = Modifier
                .height(16.dp)
        )

        Text(
            text = "",
            textAlign = TextAlign.Center,
            color = Color.Black,
            fontSize = typography.titleSmall.fontSize,
            textDecoration = TextDecoration.Underline
        )

        Spacer(
            modifier = Modifier
                .height(16.dp)
        )
    }
}

package com.molokosoft.decisionengine.newdecisionscreen.screens.quiz

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.molokosoft.decisionengine.R
import com.molokosoft.decisionengine.theme.DecisionBlue
import com.molokosoft.decisionengine.theme.DecisionBlueLight
import com.molokosoft.decisionengine.theme.DecisionGreen
import com.molokosoft.decisionengine.theme.LocalAppTypography

@Composable
fun YesNoScreen(
    question: String,
    onNextClicked: (answer: Boolean) -> Unit,
    onBackClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val typography =
        LocalAppTypography.current

    var selectedAnswer by rememberSaveable { mutableStateOf<Boolean?>(null) }

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
                .height(16.dp)
        )

        Text(
            text = question,
            textAlign = TextAlign.Center,
            fontSize = typography.titleMedium.fontSize,
            color = Color.Black
        )

        Spacer(
            modifier = Modifier
                .height(64.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            YesNoAnswerTile(
                modifier = Modifier
                    .weight(1f),
                text = "Yes",
                selected = selectedAnswer == true,
                onClick = { selectedAnswer = true }
            )

            YesNoAnswerTile(
                modifier = Modifier
                    .weight(1f),
                text = "No",
                selected = selectedAnswer == false,
                onClick = { selectedAnswer = false }
            )
        }

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
                .clickable(enabled = selectedAnswer != null) {
                    selectedAnswer?.let(onNextClicked)
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Done",
                textAlign = TextAlign.Center,
                color = Color.White
            )
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
                .clickable {
                    onBackClicked()
                }
        )

        Spacer(
            modifier = Modifier
                .height(16.dp)
        )
    }
}

@Composable
private fun YesNoAnswerTile(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val typography = LocalAppTypography.current

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(88.dp)
            .border(
                width = 1.dp,
                color = if (selected) Color.Black else DecisionBlueLight,
                shape = RoundedCornerShape(12.dp)
            )
            .background(
                color = if (selected) DecisionGreen else DecisionBlue,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            fontSize = typography.titleSmall.fontSize * 1.2f,
            color = if (selected) Color.Black else Color.White
        )
    }
}
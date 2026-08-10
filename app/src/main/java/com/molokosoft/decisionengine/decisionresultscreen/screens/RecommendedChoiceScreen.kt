package com.molokosoft.decisionengine.decisionresultscreen.screens

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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle

import com.molokosoft.decisionengine.R
import com.molokosoft.decisionengine.repositories.model.OptionAnalysis
import com.molokosoft.decisionengine.theme.DecisionBlue
import com.molokosoft.decisionengine.theme.DecisionGreen
import com.molokosoft.decisionengine.theme.LocalAppTypography
import com.molokosoft.decisionengine.theme.MostChosenGreen
import com.molokosoft.decisionengine.theme.Orange

@Composable
fun RecommendedChoiceScreen(
    modifier: Modifier = Modifier,
    optionAnalyses: List<OptionAnalysis>,
    onContinueClicked: () -> Unit
) {
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
        Image(
            painter = painterResource(id = R.drawable.pokal),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .aspectRatio(1f)
        )

        Box(
            modifier = Modifier
                .background(
                    color = MostChosenGreen,
                    shape = RoundedCornerShape(6.dp)
                )
                .padding(vertical = 2.dp, horizontal = 4.dp)
        ){
            Text(
                text = "Recommended Option",
                textAlign = TextAlign.Center,
                fontSize = typography.titleSmall.fontSize,
                color = Color.Black
            )
        }

        Spacer(
            modifier = Modifier
                .height(32.dp)
        )

        Text(
            text = optionAnalyses.first().name,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            textAlign = TextAlign.Center,
            fontSize = typography.titleLarge.fontSize * 2,
            color = Color.Black
        )

        Spacer(
            modifier = Modifier
                .height(32.dp)
        )

        val textColorForConfidenceRating = when (optionAnalyses.first().weightedScoreAsPercentage()) {
            in 0..25 -> Color.Red
            in 26..50 -> Orange
            in 51..75 -> DecisionGreen
            in 76..100 -> MostChosenGreen
            else -> Color.Gray
        }

        Text(
            text = buildAnnotatedString {
                append("${optionAnalyses.first().weightedScoreAsPercentage()}%\n")

                withStyle(
                    style = SpanStyle(
                        fontSize = typography.titleSmall.fontSize * 1.25f,
                        color = Color.Black,
                        fontWeight = FontWeight.Normal
                    ),
                ) {
                    append("Confidence Scoring")
                }
            },
            textAlign = TextAlign.Center,
            fontSize = typography.titleLarge.fontSize * 2.5,
            fontWeight = FontWeight.Bold,
            color = textColorForConfidenceRating,
            lineHeight = 36.sp
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
                .clickable() {
                    onContinueClicked()
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Why is this the best option?",
                textAlign = TextAlign.Center,
                color = Color.White
            )
        }

        Spacer(
            modifier = Modifier
                .height(32.dp)
        )
    }
}
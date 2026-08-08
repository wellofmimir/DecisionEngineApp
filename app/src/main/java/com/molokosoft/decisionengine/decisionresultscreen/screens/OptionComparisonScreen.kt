package com.molokosoft.decisionengine.decisionresultscreen.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.molokosoft.decisionengine.R

import com.molokosoft.decisionengine.repositories.model.OptionAnalysis
import com.molokosoft.decisionengine.theme.DecisionBlue
import com.molokosoft.decisionengine.theme.DecisionBlueLight
import com.molokosoft.decisionengine.theme.EasyReverseGreen
import com.molokosoft.decisionengine.theme.HardReverseRed
import com.molokosoft.decisionengine.theme.ImpossibleReverseRed
import com.molokosoft.decisionengine.theme.LocalAppTypography
import com.molokosoft.decisionengine.theme.MediumReverseOrange
import com.molokosoft.decisionengine.theme.MostChosenGreen
import com.molokosoft.decisionengine.theme.VeryEasyReverseGreen

@Composable
fun OptionBreakdownCard(
    modifier: Modifier = Modifier,
    iconResource: Int,
    optionName: String,
    optionScore: Double
){
    val typography = LocalAppTypography.current

    val impactText = when {
        optionScore <= 10.0f -> "Minor impact"
        optionScore <= 25.0f -> "Moderate impact"
        optionScore <= 50.0f -> "Significant impact"
        optionScore <= 100.0f -> "Very strong impact"
        else -> "Critical impact"
    }

    val impactColor = when (optionScore) {
        in 1.0f..10.0f -> HardReverseRed
        in 11.0f..25.0f -> MediumReverseOrange
        in 26.0f..50.0f -> EasyReverseGreen
        in 51.0f..100.0f -> VeryEasyReverseGreen
        else -> VeryEasyReverseGreen
    }

    Row(
        modifier = modifier
            .background(
                color = DecisionBlueLight,
                shape = RoundedCornerShape(12.dp)
            )
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
        Box(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .size(64.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = MostChosenGreen,
                        shape = RoundedCornerShape(6.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = iconResource),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        Column(
            modifier = Modifier
                .padding(bottom = 8.dp, end = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    text = optionName,
                    textAlign = TextAlign.Left,
                    fontSize = typography.titleMedium.fontSize * 0.8f,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black,

                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                )

                Text(
                    text = "$optionScore%",
                    textAlign = TextAlign.End,
                    fontSize = typography.titleMedium.fontSize * 0.8f,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black,
                    modifier = Modifier
                        .weight(1f)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    text = "Score: ${optionScore.toFloat()}",
                    textAlign = TextAlign.Left,
                    fontSize = LocalAppTypography.current.titleSmall.fontSize,
                    fontWeight = FontWeight.Light,
                    color = Color.Black
                )

                Text(
                    text = impactText,
                    textAlign = TextAlign.End,
                    fontSize = LocalAppTypography.current.titleSmall.fontSize,
                    fontWeight = FontWeight.Light,
                    color = Color.Black
                )
            }

            Box(
                modifier = Modifier
                    .height(8.dp)
                    .fillMaxWidth(optionScore.toFloat() / 100f)
                    .background(
                        color = impactColor,
                        shape = RoundedCornerShape(24.dp)
                    )
                    .align(Alignment.Start)
            )
        }
    }
}

@Composable
fun OptionsDifferenceCard(
    modifier: Modifier = Modifier,
    difference: Int,
    optionName: String
){
    val typography = LocalAppTypography.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(12.dp)
            )
            .background(
                color = DecisionBlueLight,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(top = 16.dp, bottom = 8.dp, start = 8.dp, end = 8.dp)
    ){
        Text(
            text = "What is the difference between the best options?",
            textAlign = TextAlign.Left,
            fontSize = typography.titleMedium.fontSize,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
            modifier = Modifier
                .padding(start = 16.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Image(
                painter = painterResource(id = R.drawable.pokal),
                contentDescription = null,
                modifier = Modifier
                    .size(128.dp)
            )

            Column(
                modifier = Modifier
                    .weight(2f),
                horizontalAlignment = Alignment.Start
            ){
                Spacer(
                    modifier = Modifier
                        .height(16.dp)
                )

                Text(
                    text = "There is a difference of $difference% between the two highest options.",
                    textAlign = TextAlign.Start,
                    fontSize = typography.titleSmall.fontSize,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )

                val text = when {
                    difference >= 20 ->
                        "The recommendation is very clear. $optionName stands out significantly from all other alternatives."

                    difference >= 10 ->
                        "$optionName has a clear advantage and is the strongest overall choice."

                    difference >= 5 ->
                        "Option $optionName performs better overall, although the alternatives remain competitive."

                    difference >= 2 ->
                        "The top options are fairly close. Small changes in priorities or scores could affect the outcome."

                    else ->
                        "The top two options are nearly tied. In this case, personal preference or additional considerations should guide your final decision."
                }

                Text(
                    text = text,
                    textAlign = TextAlign.Start,
                    fontSize = typography.titleSmall.fontSize,
                    fontWeight = FontWeight.Light,
                    color = Color.Black
                )
            }
        }
    }
}

@Composable
fun OptionComparisonScreen(
    modifier: Modifier = Modifier,
    optionAnalyses: List<OptionAnalysis>,
    onContinueButtonText: String,
    onContinueClicked: () -> Unit,
    onBackClicked: () -> Unit
){
    val typography = LocalAppTypography.current
    val verticalScroll = rememberScrollState()

    val isAtBottom by remember {
        derivedStateOf {
            verticalScroll.maxValue == 0 || verticalScroll.value >= verticalScroll.maxValue
        }
    }

    val difference = if (optionAnalyses.size > 1) {
        optionAnalyses.first().weightedScoreAsPercentage() - optionAnalyses[1].weightedScoreAsPercentage()
    } else {
        -1
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .verticalScroll(verticalScroll)
                .padding(bottom = 96.dp)

        ){
            Spacer(
                modifier = Modifier
                    .height(32.dp)
            )

            Text(
                text = "Options overview",
                textAlign = TextAlign.Center,
                fontSize = typography.titleLarge.fontSize,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                lineHeight = 32.sp
            )

            Text(
                text = "Compare all options and their overall scores.",
                textAlign = TextAlign.Center,
                fontSize = typography.titleSmall.fontSize,
                color = Color.Black,
                lineHeight = 16.sp
            )

            Spacer(
                modifier = Modifier
                    .height(16.dp)
            )

            OptionsDifferenceCard(
                difference = difference,
                optionName = optionAnalyses.first().name
            )

            Spacer(
                modifier = Modifier
                    .height(4.dp)
            )

            OptionsBreakdownSection(
                optionAnalyses = optionAnalyses
            )

            Spacer(
                modifier = Modifier
                    .height(16.dp)
            )
        }

        AnimatedVisibility(
            visible = isAtBottom,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
                .border(
                    width = 1.dp,
                    color = DecisionBlue,
                    shape = RoundedCornerShape(64.dp)
                )
                .background(
                    color = DecisionBlue,
                    shape = RoundedCornerShape(64.dp)
                )
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
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
                        onContinueClicked()
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = onContinueButtonText,
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
            }
        }
    }
}
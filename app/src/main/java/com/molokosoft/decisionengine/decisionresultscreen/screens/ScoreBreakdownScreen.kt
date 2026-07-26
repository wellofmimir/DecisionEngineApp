package com.molokosoft.decisionengine.decisionresultscreen.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.unit.sp
import com.molokosoft.decisionengine.R
import com.molokosoft.decisionengine.network.backend.model.dto.decision.DecisionAnalysisResult

import com.molokosoft.decisionengine.repositories.model.OptionAnalysis
import com.molokosoft.decisionengine.theme.DecisionBlue
import com.molokosoft.decisionengine.theme.DecisionBlueLight
import com.molokosoft.decisionengine.theme.EasyReverseGreen
import com.molokosoft.decisionengine.theme.HardReverseRed
import com.molokosoft.decisionengine.theme.LocalAppTypography
import com.molokosoft.decisionengine.theme.MostChosenGreen
import com.molokosoft.decisionengine.theme.ImpossibleReverseRed
import com.molokosoft.decisionengine.theme.MediumReverseOrange
import com.molokosoft.decisionengine.theme.VeryEasyReverseGreen

@Composable
fun KeyInsightCard(
    modifier: Modifier = Modifier,
    iconResource: Int,
    keyInsightText: String
){
    val typography = LocalAppTypography.current

    Row(
        modifier = modifier
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(12.dp)
            )
            .background(
                color = DecisionBlue,
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
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ){
                Text(
                    text = "Key Insight",
                    textAlign = TextAlign.Left,
                    fontSize = typography.titleMedium.fontSize * 0.8f,
                    fontWeight = FontWeight.Normal,
                    color = Color.White
                )

                Text(
                    text = keyInsightText,
                    textAlign = TextAlign.Left,
                    fontSize = typography.titleSmall.fontSize,
                    fontWeight = FontWeight.Light,
                    color = Color.White
                )
            }
        }
    }
}
@Composable
fun BiggestImpactCard(
    modifier: Modifier = Modifier,
    biggestFactor: String
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
            text = "What had the biggest impact?",
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
                    text = "$biggestFactor was the biggest driver behind your decision.",
                    textAlign = TextAlign.Start,
                    fontSize = typography.titleSmall.fontSize,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )

                Text(
                    text = "This factor had the strongest positive influence on the result.",
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
fun FactorBreakdownCard(
    modifier: Modifier = Modifier,
    iconResource: Int,
    factorName: String,
    factorImpact: Int,
    factorWeight: Int
){
    val typography = LocalAppTypography.current

    val impactText = when {
        factorImpact == 0 -> "No impact"
        factorImpact <= 10 -> "Minor impact"
        factorImpact <= 25 -> "Moderate impact"
        factorImpact <= 50 -> "Significant impact"
        factorImpact <= 100 -> "Very strong impact"
        else -> "Critical impact"
    }

    val impactColor = when (factorImpact) {
        0 -> ImpossibleReverseRed
        in 1..10 -> HardReverseRed
        in 11..25 -> MediumReverseOrange
        in 26..50 -> EasyReverseGreen
        in 51..100 -> VeryEasyReverseGreen
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
                    text = factorName,
                    textAlign = TextAlign.Left,
                    fontSize = typography.titleMedium.fontSize * 0.8f,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black,

                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                )

                Text(
                    text = "$factorImpact%",
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
                    text = "Importance: $factorWeight",
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
                    .fillMaxWidth(factorImpact / 100f)
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
fun FactorBreakdownSection(
    modifier: Modifier = Modifier,
    optionAnalyses: List<OptionAnalysis>
){
    val typography = LocalAppTypography.current

    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Column(
            modifier = Modifier
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(12.dp)
                )
                .background(
                    color = DecisionBlueLight,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(top = 8.dp)
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ){
                Spacer(
                    modifier = Modifier
                        .width(16.dp)
                )

                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .background(
                            color = VeryEasyReverseGreen,
                            shape = CircleShape
                        )
                )

                Spacer(
                    modifier = Modifier
                        .width(16.dp)
                )

                Text(
                    text = "High Impact",
                    fontSize = typography.titleSmall.fontSize * 0.8f,
                    color = Color.Black
                )

                Spacer(
                    modifier = Modifier
                        .width(32.dp)
                )

                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .background(
                            color = ImpossibleReverseRed,
                            shape = CircleShape
                        )
                )

                Spacer(
                    modifier = Modifier
                        .width(16.dp)
                )

                Text(
                    text = "Low Impact",
                    fontSize = typography.titleSmall.fontSize * 0.8f,
                    color = Color.Black
                )
            }

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                thickness = 1.dp,
                color = Color.LightGray
            )

            val bestOption = optionAnalyses.first()

            bestOption.analyses.forEach { criteria ->
                FactorBreakdownCard(
                    iconResource = R.drawable.star_blue_foreground,
                    factorName = criteria.name,
                    factorImpact = criteria.percentage.toInt(),
                    factorWeight = criteria.importance
                )
            }
        }
    }
}

@Composable
fun ScoreBreakdownScreen(
    modifier: Modifier = Modifier,
    decisionAnalysisResult: DecisionAnalysisResult?,
    optionAnalyses: List<OptionAnalysis>,
    onContinueButtonText: String,
    onContinueClicked: () -> Unit
){
    val typography = LocalAppTypography.current
    val verticalScroll = rememberScrollState()

    val isAtBottom by remember {
        derivedStateOf {
            verticalScroll.maxValue == 0 || verticalScroll.value >= verticalScroll.maxValue
        }
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
                text = "Factor Analysis",
                textAlign = TextAlign.Center,
                fontSize = typography.titleLarge.fontSize,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                lineHeight = 32.sp
            )

            Text(
                text = "Here is how the factors influenced your result.",
                textAlign = TextAlign.Center,
                fontSize = typography.titleSmall.fontSize,
                color = Color.Black,
                lineHeight = 16.sp
            )

            Spacer(
                modifier = Modifier
                    .height(16.dp)
            )

            BiggestImpactCard(
                biggestFactor = optionAnalyses.first().analyses.first().name
            )

            Spacer(
                modifier = Modifier
                    .height(4.dp)
            )

            OptionBreakdownSection(
                optionAnalyses = optionAnalyses
            )

            Spacer(
                modifier = Modifier
                    .height(4.dp)
            )

            if (decisionAnalysisResult != null)
                KeyInsightCard(
                    iconResource = R.drawable.eye_foreground,
                    keyInsightText = decisionAnalysisResult.whyItStandsOut
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
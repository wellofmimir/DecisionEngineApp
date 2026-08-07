package com.molokosoft.decisionengine.decisionresultscreen.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.foundation.rememberScrollState

import com.molokosoft.decisionengine.R
import com.molokosoft.decisionengine.network.backend.model.dto.decision.DecisionAnalysisResult
import com.molokosoft.decisionengine.repositories.model.OptionAnalysis
import com.molokosoft.decisionengine.theme.DecisionBlueLight
import com.molokosoft.decisionengine.theme.*
import com.molokosoft.decisionengine.theme.LocalAppTypography

@Composable
fun ReversibilityExplanationCard(
    modifier: Modifier = Modifier,
    optionAnalyses: List<OptionAnalysis>,
    reversibilityAnalysisText: String
){
    val typography = LocalAppTypography.current
    val verticalScroll = rememberScrollState()

    val title = when (optionAnalyses.first().reversibility){
        in 1 .. 2  -> "Impossible"
        in 3 .. 4 -> "Hard"
        in 5 .. 6 -> "Medium"
        in 7 .. 8 -> "Easy"
        in 9 .. 10 -> "Very Easy"
        else -> "Medium"
    }

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
            .border(
                width = 1.dp,
                color = DecisionBlueLight,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(top = 32.dp, start = 16.dp, end = 16.dp)
    ){
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
        ){
            Text(
                text = "Why is reversibility $title?",
                textAlign = TextAlign.Left,
                fontSize = typography.titleMedium.fontSize,
                color = Color.Black,
                lineHeight = 16.sp
            )

            Spacer(
                modifier = Modifier
                    .height(16.dp)
            )

            Text(
                text = reversibilityAnalysisText,
                textAlign = TextAlign.Left,
                fontSize = typography.titleSmall.fontSize,
                fontWeight = FontWeight.Normal,
                color = Color.Black,
                lineHeight = 16.sp,
                modifier = Modifier
                    .verticalScroll(verticalScroll)
            )

            Spacer(
                modifier = Modifier
                    .height(8.dp)
            )
        }
    }
}
@Composable
fun ReversibilityCard(
    modifier: Modifier = Modifier,
    optionAnalyses: List<OptionAnalysis>
){
    val typography = LocalAppTypography.current

    val imageResource = when (optionAnalyses.first().reversibility){
        in 1..2 -> R.drawable.impossible_foreground
        in 3..4 -> R.drawable.hard_foreground
        in 5..6 -> R.drawable.medium_foreground
        in 7..8 -> R.drawable.easy_foreground
        in 9..10 -> R.drawable.veryeasy_foreground
        else -> R.drawable.medium_foreground
    }

    val color = when (optionAnalyses.first().reversibility){
        in 1..2 -> ImpossibleReverseRed
        in 3..4 -> HardReverseRed
        in 5..6 -> MediumReverseOrange
        in 7..8 -> EasyReverseGreen
        in 9..10 -> VeryEasyReverseGreen
        else -> MediumReverseOrange
    }

    val title = when (optionAnalyses.first().reversibility){
        in 1 .. 2  -> "Impossible"
        in 3 .. 4 -> "Hard"
        in 5 .. 6 -> "Medium"
        in 7 .. 8 -> "Easy"
        in 9 .. 10 -> "Very Easy"
        else -> "Medium"
    }

    val description = when (optionAnalyses.first().reversibility) {
        in 1..2 ->
            "This decision is extremely difficult or impossible to reverse. Consider it carefully before committing."

        in 3..4 ->
            "Reversing this decision would require significant time, effort, or resources. Proceed with caution."

        in 5..6 ->
            "This decision is moderately reversible. Changing course is possible but may involve some cost or inconvenience."

        in 7..8 ->
            "This decision can be reversed with relatively little effort. You have room to adjust if needed."

        in 9..10 ->
            "This decision is highly reversible. You can easily change your mind later with minimal consequences."

        else ->
            "The reversibility of this decision is moderate."
    }

    Column(
        modifier = modifier
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(12.dp)
            )
            .background(
                color = DecisionBlueLight,
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                width = 1.dp,
                color = DecisionBlueLight,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(vertical = 32.dp, horizontal = 8.dp)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            Spacer(
                modifier = Modifier
                    .width(16.dp)
            )

            Image(
                painter = painterResource(id = imageResource),
                contentDescription = null,
                modifier = Modifier
                    .size(64.dp, 64.dp)
            )

            Spacer(
                modifier = Modifier
                    .width(16.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ){
                Text(
                    text = title,
                    textAlign = TextAlign.Left,
                    fontSize = typography.titleMedium.fontSize,
                    fontWeight = FontWeight.SemiBold,
                    color = color,
                    lineHeight = 32.sp
                )

                Spacer(
                    modifier = Modifier
                        .height(8.dp)
                )

                Text(
                    text = description,
                    textAlign = TextAlign.Left,
                    fontSize = typography.titleSmall.fontSize,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    lineHeight = 16.sp
                )
            }
        }

        Spacer(
            modifier = Modifier
                .weight(1f)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val reversibility = optionAnalyses.first().reversibility
            val filledBars = (reversibility + 1) / 2

            repeat(5) { index ->
                Box(
                    modifier = Modifier
                        .shadow(
                            elevation = 8.dp,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .weight(1f)
                        .height(12.dp)
                        .background(
                            color = if (index < filledBars) color else Color.Gray,
                            shape = RoundedCornerShape(48.dp)
                        )
                )
            }
        }

        Spacer(
            modifier = Modifier
                .width(16.dp)
        )
    }
}

@Composable
fun ReversibilityAnalysisScreen(
    modifier: Modifier = Modifier,
    optionAnalyses: List<OptionAnalysis>,
    decisionAnalysisResult: DecisionAnalysisResult?,
    onContinueClicked: () -> Unit
){
    val typography = LocalAppTypography.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(horizontal = 8.dp)
        ){
            Spacer(
                modifier = Modifier
                    .height(32.dp)
            )

            Text(
                text = "Reversibility Analysis",
                textAlign = TextAlign.Center,
                fontSize = typography.titleLarge.fontSize,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                lineHeight = 32.sp
            )

            Text(
                text = "How easy would it be to reverse this decision\nif it doesn't work out?",
                textAlign = TextAlign.Center,
                fontSize = typography.titleSmall.fontSize,
                color = Color.Black,
                lineHeight = 16.sp
            )

            Spacer(
                modifier = Modifier
                    .height(16.dp)
            )

            ReversibilityCard(
                modifier = Modifier
                    .fillMaxHeight(0.35f),
                optionAnalyses = optionAnalyses
            )

            Spacer(
                modifier = Modifier
                    .height(16.dp)
            )
        }

        ReversibilityExplanationCard(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .weight(1f),
            optionAnalyses = optionAnalyses,
            reversibilityAnalysisText = decisionAnalysisResult?.reversibility ?: ""
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
                    onContinueClicked()
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Next",
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
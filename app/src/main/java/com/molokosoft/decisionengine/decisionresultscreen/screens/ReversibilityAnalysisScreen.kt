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
import androidx.compose.foundation.gestures.scrollable
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
import com.molokosoft.decisionengine.repositories.model.OptionAnalysis
import com.molokosoft.decisionengine.theme.DecisionBlueLight
import com.molokosoft.decisionengine.theme.*
import com.molokosoft.decisionengine.theme.LocalAppTypography

@Composable
fun ReversibilityExplanationCard(
    modifier: Modifier = Modifier,
    optionAnalyses: List<OptionAnalysis>,
    onSeeFullAnalysisClicked: () -> Unit,
){
    val typography = LocalAppTypography.current
    val verticalScroll = rememberScrollState()

    val imageResource = when (optionAnalyses.first().reversibility){
        1 -> R.drawable.impossible_reverse_hook_foreground
        2 -> R.drawable.hard_reverse_hook_foreground
        3 -> R.drawable.medium_reverse_hook_foreground
        4 -> R.drawable.easy_reverse_hook_foreground
        5 -> R.drawable.very_easy_reverse_hook_foreground
        else -> R.drawable.medium_reverse_hook_foreground
    }

    val title = when (optionAnalyses.first().reversibility){
        1 -> "impossible"
        2 -> "hard"
        3 -> "medium"
        4 -> "easy"
        5 -> "very Easy"
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
                .fillMaxHeight(0.75f)
                .verticalScroll(verticalScroll)
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

//            optionAnalysis.first().reversibility.forEachIndexed { index, string ->
//                Row(
//                    modifier = Modifier
//                        .padding(start = 16.dp),
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.Center
//                ){
//                    Image(
//                        painter = painterResource(id = imageResource),
//                        contentDescription = null,
//                        modifier = Modifier
//                            .size(32.dp, 32.dp)
//                    )
//
//                    Spacer(
//                        modifier = Modifier
//                            .width(16.dp)
//                    )
//
//                    Text(
//                        text = string,
//                        textAlign = TextAlign.Left,
//                        fontSize = typography.titleSmall.fontSize,
//                        color = Color.Black,
//                        lineHeight = 16.sp
//                    )
//                }
//
//                Spacer(
//                    modifier = Modifier
//                        .height(8.dp)
//                )
//            }
        }

        Spacer(
            modifier = Modifier
                .weight(1f)
        )

        Text(
            text = "Tap here\nto see full reversibility analysis",
            textAlign = TextAlign.Center,
            fontSize = typography.titleSmall.fontSize,
            textDecoration = TextDecoration.Underline,
            color = Color.Black,
            lineHeight = 16.sp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clickable(){
                    onSeeFullAnalysisClicked()
                }
        )

        Spacer(
            modifier = Modifier
                .height(8.dp)
        )
    }
}
@Composable
fun ReversibilityCard(
    modifier: Modifier = Modifier,
    optionAnalyses: List<OptionAnalysis>
){
    val typography = LocalAppTypography.current

    val imageResource = when (optionAnalyses.first().reversibility){
        1 -> R.drawable.impossible_foreground
        2 -> R.drawable.hard_foreground
        3 -> R.drawable.medium_foreground
        4 -> R.drawable.easy_foreground
        5 -> R.drawable.veryeasy_foreground
        else -> R.drawable.medium_foreground
    }

    val color = when (optionAnalyses.first().reversibility){
        1 -> ImpossibleReverseRed
        2 -> HardReverseRed
        3 -> MediumReverseOrange
        4 -> EasyReverseGreen
        5 -> VeryEasyReverseGreen
        else -> MediumReverseOrange
    }

    val title = when (optionAnalyses.first().reversibility){
        1 -> "Impossible"
        2 -> "Hard"
        3 -> "Medium"
        4 -> "Easy"
        5 -> "Very Easy"
        else -> "Medium"
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

                Text(
                    text = "reversibility bla bla",
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
            repeat(5) {
                Box(
                    modifier = Modifier
                        .shadow(
                            elevation = 8.dp,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .weight(1f)
                        .height(12.dp)
                        .background(
                            color = if (it < optionAnalyses.first().reversibility) color else Color.Gray,
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
fun ReversabilityAnalysisScreen(
    modifier: Modifier = Modifier,
    optionAnalyses: List<OptionAnalysis>,
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
            onSeeFullAnalysisClicked = {

            }
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
                .shadow(
                    elevation = 8.dp,
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
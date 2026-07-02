package com.molokosoft.decisionengine.decisionresultscreen.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue

import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Alignment

import androidx.compose.ui.res.painterResource
import com.molokosoft.decisionengine.R
import com.molokosoft.decisionengine.network.backend.model.dto.DecisionAnalysisResult

import com.molokosoft.decisionengine.theme.*

@Composable
fun AnalysisCard(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    iconResource: Int,
    backgroundColor: Color,
    onClicked: () -> Unit
) {
    val typography = LocalAppTypography.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(12.dp)
            )
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(8.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth(0.2f)
                .padding(top = 6.dp, end = 8.dp)

        ) {
            Image(
                painter = painterResource(id = iconResource),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .border(
                        shape = RoundedCornerShape(5.dp),
                        color = DecisionBlue,
                        width = 1.dp
                    )
                    .padding(8.dp)
            )
        }

        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = title,
                fontSize = typography.titleMedium.fontSize,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )

            Text(
                text = description,
                fontSize = typography.titleSmall.fontSize,
                fontWeight = FontWeight.Light,
                color = Color.Black
            )

            Text(
                text = "Tap here to see more",
                textDecoration = TextDecoration.Underline,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .clickable {
                        onClicked()
                    }
            )
        }
    }
}

@Composable
fun FullDecisionOverviewScreen(
    modifier: Modifier = Modifier,
    decisionAnalysisResult: DecisionAnalysisResult?,
    onContinueClicked: () -> Unit
){
    val typography = LocalAppTypography.current
    val verticalScroll = rememberScrollState()

    val isAtBottom by remember {
        derivedStateOf {
            verticalScroll.value > verticalScroll.maxValue - verticalScroll.maxValue / 2
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
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
                text = "Full Decision Analysis",
                textAlign = TextAlign.Center,
                fontSize = typography.titleLarge.fontSize,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                lineHeight = 32.sp
            )

            Text(
                text = "Here is what my analysis tell me about\nyour decision.",
                textAlign = TextAlign.Center,
                fontSize = typography.titleSmall.fontSize,
                color = Color.Black,
                lineHeight = 16.sp
            )

            Spacer(
                modifier = Modifier
                    .height(16.dp)
            )

            AnalysisCard(
                title = "Summary",
                description = decisionAnalysisResult?.summary ?: "",
                iconResource = R.drawable.star_blue_foreground,
                backgroundColor = SummaryBackground,
                onClicked = {

                }
            )

            Spacer(
                modifier = Modifier
                    .height(8.dp)
            )

            AnalysisCard(
                title = "Potential Blind Spots",
                description = decisionAnalysisResult?.blindSpots ?: "",
                iconResource = R.drawable.eye_foreground,
                backgroundColor = BlindSpotBackground,
                onClicked = {

                }
            )

            Spacer(
                modifier = Modifier
                    .height(8.dp)
            )

            AnalysisCard(
                title = "Roadmap to Success",
                description = decisionAnalysisResult?.roadmapToSuccess ?: "",
                iconResource = R.drawable.upwards_trend_file_foreground,
                backgroundColor = OutlookBackground,
                onClicked = {

                }
            )

            Spacer(
                modifier = Modifier
                    .height(8.dp)
            )

            AnalysisCard(
                title = "Conclusion",
                description = decisionAnalysisResult?.conclusion ?: "",
                iconResource = R.drawable.search_foreground,
                backgroundColor = InsightBackground,
                onClicked = {

                }
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
                .shadow(
                    elevation = 8.dp,
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
                    text = "Finish Analysis",
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
            }
        }
    }
}
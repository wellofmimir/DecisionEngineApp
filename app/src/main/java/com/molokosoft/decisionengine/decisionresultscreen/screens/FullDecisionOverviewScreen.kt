package com.molokosoft.decisionengine.decisionresultscreen.screens

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
import androidx.compose.foundation.layout.Row

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.shape.CircleShape

import com.molokosoft.decisionengine.R
import com.molokosoft.decisionengine.network.backend.model.dto.DecisionAnalysisResult
import com.molokosoft.decisionengine.theme.*

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider

@Composable
fun PagerIndicator(
    pageCount: Int,
    currentPage: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(pageCount) { index ->
            Box(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .size(if (index == currentPage) 10.dp else 8.dp)
                    .background(
                        color = if (index == currentPage)
                            DecisionBlue
                        else
                            Color.LightGray,
                        shape = CircleShape
                    )
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalysisPager(
    modifier: Modifier = Modifier,
    decisionAnalysisResult: DecisionAnalysisResult
){
    val pages = listOf(
        Triple(
            "Summary",
            decisionAnalysisResult.summary,
            SummaryBackground
        ),
        Triple(
            "Potential Blind Spots",
            decisionAnalysisResult.blindSpots,
            BlindSpotBackground
        ),
        Triple(
            "Roadmap to Success",
            decisionAnalysisResult.roadmapToSuccess,
            OutlookBackground
        ),
        Triple(
            "Conclusion",
            decisionAnalysisResult.conclusion,
            InsightBackground
        )
    )

    val pagerState = rememberPagerState {
        pages.size
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxHeight()
    ){
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
        ){ page ->
            AnalysisPage(
                modifier = Modifier
                    .padding(16.dp),
                title = pages[page].first,
                description = pages[page].second,
                iconResource = R.drawable.star_blue_foreground,
                backgroundColor = pages[page].third
            )
        }

        Spacer(
            Modifier
                .height(4.dp)
        )

        PagerIndicator(
            pageCount = pages.size,
            currentPage = pagerState.currentPage
        )
    }
}

@Composable
fun AnalysisPage(
    title: String,
    description: String,
    iconResource: Int,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    val typography = LocalAppTypography.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(24.dp)
            )
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(24.dp)
            )
            .padding(start = 24.dp, end = 24.dp, top = 24.dp, bottom = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            painter = painterResource(iconResource),
            contentDescription = null,
            modifier = Modifier
                .size(72.dp)
                .border(
                    width = 2.dp,
                    color = DecisionBlue,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(16.dp)
        )

        Spacer(
            Modifier
                .height(16.dp)
        )

        Text(
            text = title,
            fontSize = typography.titleLarge.fontSize,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Justify
        )

        Spacer(
            Modifier
                .height(16.dp)
        )

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth(),
            color = DecisionBlue.copy(alpha = 0.3f)
        )

        Spacer(
            Modifier
                .height(16.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
        ) {
            val scrollState = rememberScrollState()

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .verticalScroll(scrollState)
            ) {
                Text(
                    text = description,
                    fontSize = typography.bodyLarge.fontSize,
                    lineHeight = 26.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            }
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

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
    ) {
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
            text = "Here is what my analysis tells me about\nyour decision.",
            textAlign = TextAlign.Center,
            fontSize = typography.titleSmall.fontSize,
            color = Color.Black,
            lineHeight = 16.sp
        )

        Spacer(
            modifier = Modifier
                .height(32.dp)
        )

        AnalysisPager(
            modifier = Modifier
                .weight(1f),
            decisionAnalysisResult = decisionAnalysisResult!!
        )

        Spacer(
            modifier = Modifier
                .height(8.dp)
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
                text = "Finish Analysis",
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
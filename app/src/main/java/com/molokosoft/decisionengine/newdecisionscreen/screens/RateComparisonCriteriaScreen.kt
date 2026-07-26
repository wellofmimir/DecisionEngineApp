package com.molokosoft.decisionengine.newdecisionscreen.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults

import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf

import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp

import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString

import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp

import com.molokosoft.decisionengine.R
import com.molokosoft.decisionengine.theme.DecisionBlue
import com.molokosoft.decisionengine.theme.DecisionBlueLight
import com.molokosoft.decisionengine.theme.LocalAppTypography

@Composable
fun CriteriaWithSlider(
    modifier: Modifier = Modifier,
    criteria: String,
    onValueChange: (Float) -> Unit
){
    val typography = LocalAppTypography.current
    var score by remember { mutableFloatStateOf(5f) }

    Row(
        modifier = modifier
            .height(96.dp)
            .fillMaxWidth(0.9f)
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(4.dp)
            )
            .background(
                color = DecisionBlueLight,
                shape = RoundedCornerShape(4.dp)
            )
            .border(
                width = 1.dp,
                color = DecisionBlueLight,
                shape = RoundedCornerShape(4.dp)
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Spacer(
            modifier = Modifier
                .width(8.dp)
        )

        Text(
            text = criteria,
            textAlign = TextAlign.Left,
            color = Color.Black,
            modifier = Modifier
                .weight(2f)
        )

        Spacer(
            modifier = Modifier
                .width(8.dp)
        )

        Column(
            modifier = Modifier
                .weight(1.5f),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Slider(
                modifier = Modifier
                    .fillMaxWidth(),
                value = score,
                onValueChange = {
                    score = it
                    onValueChange(score)
                },
                valueRange = 1f..10f,
                steps = 8,
                colors = SliderDefaults.colors(
                    thumbColor = DecisionBlue,
                    activeTrackColor = DecisionBlue,
                    inactiveTrackColor = DecisionBlueLight,
                    activeTickColor = Color.White,
                    inactiveTickColor = DecisionBlue
                )
            )

            Spacer(
                modifier = Modifier
                    .width(8.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            SpanStyle(
                                fontSize = typography.titleSmall.fontSize * 1.25f
                            )
                        ) {
                            append("1")
                        }

                        append("\n")

                        withStyle(
                            SpanStyle(
                                fontSize = typography.labelSmall.fontSize,
                                color = Color.DarkGray
                            )
                        ) {
                            append("Not good")
                        }
                    },
                    textAlign = TextAlign.Start,
                    color = Color.Black,
                    lineHeight = 10.sp,
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            SpanStyle(
                                fontSize = typography.titleSmall.fontSize * 1.25f
                            )
                        ) {
                            append("10")
                        }

                        append("\n")

                        withStyle(
                            SpanStyle(
                                fontSize = typography.labelSmall.fontSize,
                                color = Color.DarkGray
                            )
                        ) {
                            append("Very good")
                        }
                    },
                    textAlign = TextAlign.End,
                    color = Color.Black,
                    lineHeight = 10.sp,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(
            modifier = Modifier
                .width(8.dp)
        )
    }
}

@Composable
fun RateComparisonCriteriaScreen(
    modifier: Modifier = Modifier,
    option: String,
    criteria: List<String>,
    onBackClicked: () -> Unit,
    onNextClicked: (criteriaToRating: List<Pair<String, Float>>) -> Unit
){
    val typography = LocalAppTypography.current

    val ratings = remember(criteria) {
        mutableStateMapOf<String, Float>().apply {
            criteria.forEach { criterion ->
                put(criterion, 0f)
            }
        }
    }

    Column(
        modifier = modifier
            .background(
                color = Color.White
            )
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
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
            text = "Rate the criteria for:",
            textAlign = TextAlign.Center,
            fontSize = typography.titleMedium.fontSize,
            color = Color.Black
        )

        Text(
            text = option,
            textAlign = TextAlign.Center,
            fontSize = typography.titleMedium.fontSize,
            textDecoration = TextDecoration.Underline,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        )

        Spacer(
            modifier = Modifier
                .height(16.dp)
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            items(criteria) { criterion ->
                CriteriaWithSlider(
                    criteria = criterion,
                    onValueChange = { newValue ->
                        ratings[criterion] = newValue
                    }
                )

                Spacer(
                    modifier = Modifier
                        .height(4.dp)
                )
            }
        }

        Spacer(
            modifier = Modifier
                .height(32.dp)
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
                    onNextClicked(
                        ratings.map { (criterion, rating) ->
                            criterion to rating
                        }
                    )
                },
            contentAlignment = Alignment.Center
        ){
            Text(
                text = "Next",
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
                .clickable() {
                    onBackClicked()
                }
        )

        Spacer(
            modifier = Modifier
                .height(16.dp)
        )
    }
}
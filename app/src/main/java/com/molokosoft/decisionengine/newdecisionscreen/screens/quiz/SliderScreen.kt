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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf

import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.molokosoft.decisionengine.R

import com.molokosoft.decisionengine.theme.DecisionBlue
import com.molokosoft.decisionengine.theme.DecisionBlueLight
import com.molokosoft.decisionengine.theme.DecisionBlueMediumLight

import com.molokosoft.decisionengine.theme.LocalAppTypography
import kotlin.math.roundToInt

@Composable
fun SliderTile(
    text: String,
    score: Float,
    onValueChange: (score: Float) -> Unit,
    modifier: Modifier = Modifier,
) {
    val typography =
        LocalAppTypography.current

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
                .width(16.dp)
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
                onValueChange = { value ->
                    onValueChange(value.roundToInt().toFloat())
                },
                valueRange = 1f..10f,
                steps = 8,
                colors = SliderDefaults.colors(
                    thumbColor = Color.Black,
                    activeTrackColor = DecisionBlue,
                    inactiveTrackColor = DecisionBlueMediumLight,
                    activeTickColor = Color.Black,
                    inactiveTickColor = Color.White
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
                            append("Not Important")
                        }
                    },
                    textAlign = TextAlign.Start,
                    color = Color.Black,
                    lineHeight = 10.sp,
                    modifier = Modifier
                        .weight(1f)
                )

                Text(
                    text = score.toInt().toString(),
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = typography.titleSmall.fontSize * 1.75f
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
                            append("Very Important")
                        }
                    },
                    textAlign = TextAlign.End,
                    color = Color.Black,
                    lineHeight = 10.sp,
                    modifier = Modifier
                        .weight(1f)
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
fun SliderScreen(
    question: String,
    onNextClicked: () -> Unit,
    onBackClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val typography = LocalAppTypography.current
    var score by remember { mutableFloatStateOf(5f) }

    Column(
        modifier = modifier
            .background(color = Color.White)
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
            text = question,
            textAlign = TextAlign.Center,
            fontSize = typography.titleMedium.fontSize,
            color = Color.Black
        )

        Spacer(
            modifier = Modifier
                .height(64.dp)
        )

        SliderTile(
            modifier = Modifier
                .heightIn(128.dp, 256.dp)
                .fillMaxWidth()
                .padding(horizontal = 2.dp),
            text = question,
            score = score,
            onValueChange = { it ->
                score = it
            }
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
                    onNextClicked()
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
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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults

import androidx.compose.material3.Text
import kotlin.math.roundToInt
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateMapOf

import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString

import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.lerp

import com.molokosoft.decisionengine.R
import com.molokosoft.decisionengine.commonuielements.dialogs.ErrorDialog
import com.molokosoft.decisionengine.newdecisionscreen.dialogs.CriteriaRatingInfoDialog
import com.molokosoft.decisionengine.theme.DecisionBlue
import com.molokosoft.decisionengine.theme.DecisionBlueLight
import com.molokosoft.decisionengine.theme.LocalAppTypography
import com.molokosoft.decisionengine.theme.*

@Composable
fun CriteriaWithSlider(
    modifier: Modifier = Modifier,
    criteria: String,
    score: Float,
    onValueChange: (Float) -> Unit,
    onInfoButtonClicked: () -> Unit
){
    val typography =
        LocalAppTypography.current

    fun smoothStep(t: Float): Float {
        val x = t.coerceIn(0f, 1f)
        return x * x * (3f - 2f * x)
    }

    val normalizedScore =
        ((score - 1f) / 9f).coerceIn(0f, 1f)

    val scoreColor = when {
        normalizedScore < 0.25f -> {
            lerp(
                ImpossibleReverseRed,
                HardReverseRed,
                smoothStep(normalizedScore / 0.25f)
            )
        }

        normalizedScore < 0.5f -> {
            lerp(
                HardReverseRed,
                MediumReverseOrange,
                smoothStep((normalizedScore - 0.25f) / 0.25f)
            )
        }

        normalizedScore < 0.75f -> {
            lerp(
                MediumReverseOrange,
                EasyReverseGreen,
                smoothStep((normalizedScore - 0.5f) / 0.25f)
            )
        }

        else -> {
            lerp(
                EasyReverseGreen,
                VeryEasyReverseGreen,
                smoothStep((normalizedScore - 0.75f) / 0.25f)
            )
        }
    }

    val animatedScoreColor by animateColorAsState(
        targetValue = scoreColor,
        animationSpec = tween(
            durationMillis = 300
        ),
        label = "scoreColor"
    )

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

        Box(
            modifier = Modifier
                .size(22.dp)
                .background(
                    color = Color.Transparent,
                    shape = CircleShape
                )
                .clickable {
                    onInfoButtonClicked()
                },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.information_foreground),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(
            modifier = Modifier
                .width(8.dp)
        )

        Text(
            text = criteria,
            textAlign = TextAlign.Left,
            color = Color.Black,
            modifier = Modifier
                .weight(1.75f)
        )

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
                modifier = Modifier.fillMaxWidth(),
                value = score,
                onValueChange = { value ->
                    onValueChange(value.roundToInt().toFloat())
                },
                valueRange = 1f..10f,
                steps = 8,
                colors = SliderDefaults.colors(
                    thumbColor = animatedScoreColor,
                    activeTrackColor = animatedScoreColor,
                    inactiveTrackColor = animatedScoreColor,
                    activeTickColor = Color.White,
                    inactiveTickColor = animatedScoreColor
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
                    modifier = Modifier
                        .weight(1f)
                )

                Text(
                    text = score.toInt().toString(),
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = typography.titleSmall.fontSize * 1.15f
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
fun RateComparisonCriteriaScreen(
    modifier: Modifier = Modifier,
    showRatingInfoDialog: Boolean,
    onInfoDialogClicked: () -> Unit,
    option: String,
    criterionAndDescription: List<Pair<String, String>>,
    onBackClicked: () -> Unit,
    onNextClicked: (criteriaToRating: List<Pair<String, Float>>) -> Unit
) {
    val typography =
        LocalAppTypography.current

    val criteria = criterionAndDescription.map { it ->
        it.first
    }

    val ratings = remember(criteria) {
        mutableStateMapOf<String, Float>().apply {
            criteria.forEach { criterion ->
                put(criterion, 5f)
            }
        }
    }

    var showExplanationDialog by remember { mutableStateOf(false) }
    var infoTextAndDescription by remember { mutableStateOf("" to "") }

    if (showRatingInfoDialog) {
        CriteriaRatingInfoDialog(
            onDismissRequest = {
                onInfoDialogClicked()
            }
        )
    } else if (showExplanationDialog) {
        //ErrorDialog wird hier als InfoDialog verwendet
        ErrorDialog(
            errorTitle = infoTextAndDescription.first,
            errorMessage = infoTextAndDescription.second,
            errorButtonText = "Okay",
            dismissButtonText = "",
            onDismissRequest = {
                showExplanationDialog = false
            },
            onAcceptRequest = {
                showExplanationDialog = false
            }
        )
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
            verticalArrangement = Arrangement.Top
        ){
            items(criteria) { criterion ->
                CriteriaWithSlider(
                    criteria = criterion,
                    score = ratings[criterion] ?: 5f,
                    onValueChange = { newValue ->
                        ratings[criterion] = newValue
                    },
                    onInfoButtonClicked = {
                        val description = criterionAndDescription.find {
                            it.first == criterion
                        }

                        if (description == null)
                            return@CriteriaWithSlider

                        infoTextAndDescription = Pair(first = criterion, second = description.second)
                        showExplanationDialog = true
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

                    ratings.keys.forEach { criterion ->
                        ratings[criterion] = 5f
                    }
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
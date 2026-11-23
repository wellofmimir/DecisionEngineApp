package com.molokosoft.decisionengine.paywall.one

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.Row

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp

import com.molokosoft.decisionengine.R
import com.molokosoft.decisionengine.theme.DecisionBlue
import com.molokosoft.decisionengine.theme.DecisionBlueLight
import com.molokosoft.decisionengine.theme.LocalAppTypography
import com.molokosoft.decisionengine.theme.SoftMint
import com.molokosoft.decisionengine.theme.SoftLavender
import com.molokosoft.decisionengine.theme.SoftOrange

@Composable
fun FeatureTile(
    modifier: Modifier = Modifier,
    imageResource: Int,
    title: String,
    text: String,
    textColor: Color,
    backgroundColor: Color,
    onClicked: () -> Unit
) {
    Column(
        modifier = modifier
            .aspectRatio(1f)
            .background(
                shape = RoundedCornerShape(12.dp),
                color = backgroundColor
            )
            .border(
                width = 1.dp,
                color = DecisionBlue,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            textAlign = TextAlign.Center,
            fontSize = LocalAppTypography.current.bodyMedium.fontSize * 1.25f,
            fontWeight = FontWeight.SemiBold,
            color = textColor
        )

        Spacer(
            modifier = Modifier
                .height(8.dp)
        )

        Image(
            painter = painterResource(id = imageResource),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(0.25f)
                .aspectRatio(1f)
        )

        Spacer(
            modifier = Modifier
                .height(8.dp)
        )

        Text(
            text = text,
            textAlign = TextAlign.Center,
            fontSize = LocalAppTypography.current.bodyMedium.fontSize,
            fontWeight = FontWeight.SemiBold,
            color = textColor
        )

        Spacer(
            modifier = Modifier
                .weight(1f)
        )

        Spacer(
            modifier = Modifier
                .height(8.dp)
        )

        val shape =

        Box(
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .height(36.dp)
                .background(
                    color = DecisionBlue,
                    shape = RoundedCornerShape(12.dp)
                )
                .border(
                    width = 1.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(12.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Tap to reveal",
                textAlign = TextAlign.Center,
                fontSize = LocalAppTypography.current.bodyMedium.fontSize,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }

        Spacer(
            modifier = Modifier
                .height(8.dp)
        )
    }
}

@Composable
fun FeaturesScreen(
    modifier: Modifier = Modifier,
    onContinueClicked: () -> Unit,
    onDeclineClicked: () -> Unit
){
    val typography = LocalAppTypography.current

    Column(
        modifier = modifier
            .background(
                shape = RoundedCornerShape(12.dp),
                color = Color.White
            )
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(
            modifier = Modifier
                .height(32.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.decisionenginelogonew),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(0.25f)
                .aspectRatio(1f)
        )

        Spacer(
            modifier = Modifier
                .height(16.dp)
        )

        Text(
            text = buildAnnotatedString {
                append("There's More to Your\n")

                withStyle(
                    style = SpanStyle(
                        color = DecisionBlue,
                        fontSize = typography.titleLarge.fontSize,
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    append("Decision.")
                }
            },
            textAlign = TextAlign.Center,
            fontSize = typography.titleLarge.fontSize,
            color = Color.Black
        )

        Spacer(
            modifier = Modifier
                .height(8.dp)
        )

        Text(
            text = "Tap each card to uncover what your results might reveal.",
            textAlign = TextAlign.Center,
            fontSize = typography.titleSmall.fontSize * 1.1f,
            color = Color.Black,
            modifier = Modifier
                .padding(horizontal = 2.dp)
        )

        Spacer(
            modifier = Modifier
                .height(16.dp)
        )

        Column(
            modifier = Modifier
                .padding(horizontal = 2.dp)
                .fillMaxWidth()
                .aspectRatio(1f),
            verticalArrangement = Arrangement.spacedBy(2.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                FeatureTile(
                    modifier = Modifier
                        .weight(1f),
                    imageResource = R.drawable.diagnosis_foreground,
                    title = "Hidden Biases",
                    text = "What might be influencing you?",
                    textColor = Color.Black,
                    backgroundColor = DecisionBlueLight,
                    onClicked = {

                    }
                )

                FeatureTile(
                    modifier = Modifier
                        .weight(1f),
                    imageResource = R.drawable.eye_foreground,
                    title = "Hidden Factors",
                    text = "What are you overlooking?",
                    textColor = Color.Black,
                    backgroundColor = SoftMint,
                    onClicked = {

                    }
                )
            }

            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                FeatureTile(
                    modifier = Modifier
                        .weight(1f),
                    imageResource = R.drawable.analysis_foreground,
                    title = "Your Results",
                    text = "What do your scores really mean?",
                    textColor = Color.Black,
                    backgroundColor = SoftLavender,
                    onClicked = {

                    }
                )

                FeatureTile(
                    modifier = Modifier
                        .weight(1f),
                    imageResource = R.drawable.upwards_trend_file_foreground,
                    title = "Your Next Steps",
                    text = "Where should you go from here?",
                    textColor = Color.Black,
                    backgroundColor = SoftOrange,
                    onClicked = {

                    }
                )
            }
        }

        Spacer(
            modifier = Modifier
                .weight(1f)
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
        ){
            Text(
                text = "Unlock Your Insights",
                textAlign = TextAlign.Center,
                color = Color.White
            )
        }
    }
}
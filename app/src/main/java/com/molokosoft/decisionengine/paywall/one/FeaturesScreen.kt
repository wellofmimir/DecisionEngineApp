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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp

import com.molokosoft.decisionengine.R
import com.molokosoft.decisionengine.theme.DecisionBlue
import com.molokosoft.decisionengine.theme.DecisionBlueLight
import com.molokosoft.decisionengine.theme.LocalAppTypography
import com.molokosoft.decisionengine.paywall.common.FeatureCard

@Composable
fun FeaturesScreen(
    modifier: Modifier = Modifier,
    onContinueClicked: () -> Unit
){
    val typography = LocalAppTypography.current

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
                .padding(top = 8.dp)
                .fillMaxWidth(0.5f)
                .aspectRatio(1f)
        )

        Spacer(
            modifier = Modifier
                .height(16.dp)
        )

        Text(
            text = "Your analysis is",
            textAlign = TextAlign.Center,
            fontSize = typography.titleLarge.fontSize,
            color = Color.Black
        )

        Text(
            text = "ready.",
            textAlign = TextAlign.Center,
            fontSize = typography.titleLarge.fontSize,
            color = Color.Black
        )

        Spacer(
            modifier = Modifier
                .height(16.dp)
        )

        Text(
            text = "Unlock your personal decision report.",
            textAlign = TextAlign.Center,
            fontSize = typography.titleMedium.fontSize,
            color = Color.Black
        )

        Spacer(
            modifier = Modifier
                .height(16.dp)
        )

        val features = listOf(
            FeatureCard(
                imageResource = R.drawable.eye_foreground,
                text = "Turn uncertainty into clarity."
            ),

            FeatureCard(
                imageResource = R.drawable.diagnosis_foreground,
                text = "Advanced AI driven insights."
            ),

            FeatureCard(
                imageResource = R.drawable.document_foreground,
                text = "Export your decisions as PDF."
            ),

            FeatureCard(
                imageResource = R.drawable.wrench_foreground,
                text = "Less overthinking. More action."
            )
        )

        features.forEach {
            Row(
                modifier = Modifier
                    .height(64.dp)
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
                horizontalArrangement = Arrangement.Center
            ){
                Spacer(
                    modifier = Modifier
                        .width(16.dp)
                )

                Image(
                    painter = painterResource(id = it.imageResource),
                    contentDescription = null,
                    modifier = Modifier
                        .size(48.dp, 48.dp)
                )

                Text(
                    text = it.text,
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Spacer(
                    modifier = Modifier
                        .width(8.dp)
                )
            }

            Spacer(
                modifier = Modifier
                    .height(4.dp)
            )
        }

        Spacer(
            modifier = Modifier
                .weight(1f)
        )

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
                .shadow(
                    elevation = 8.dp,
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
                text = "See our offer",
                textAlign = TextAlign.Center,
                color = Color.White
            )
        }

        Spacer(
            modifier = Modifier
                .height(16.dp)
        )

        Text(
            text = "",
            textAlign = TextAlign.Center,
            color = Color.Black,
            fontSize = typography.titleSmall.fontSize,
            textDecoration = TextDecoration.Underline
        )

        Spacer(
            modifier = Modifier
                .height(16.dp)
        )
    }
}
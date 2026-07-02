package com.molokosoft.decisionengine.homescreen

import com.molokosoft.decisionengine.theme.LocalAppTypography
import com.molokosoft.decisionengine.theme.DecisionBlue
import com.molokosoft.decisionengine.theme.DecisionBlueLight

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height

import androidx.compose.ui.graphics.Color

@Composable
fun UpdateToProSection(
    modifier: Modifier = Modifier
){
    val typography = LocalAppTypography.current

    Column(
        modifier = modifier
            .aspectRatio(1f)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                width = 1.dp,
                color = DecisionBlueLight,
                shape = RoundedCornerShape(12.dp)
            )
            .background(
                color = DecisionBlueLight,
                shape = RoundedCornerShape(12.dp)
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Column(
            modifier = Modifier
                .weight(2f),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = "Want",
                fontSize = typography.titleSmall.fontSize,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                color = Color.Black,
                lineHeight = 2.sp
            )

            Text(
                text = "Deeper Insights?",
                fontSize = typography.titleSmall.fontSize,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                color = Color.Black,
                lineHeight = 2.sp
            )

            Text(
                text = "Go Pro!",
                fontSize = typography.titleMedium.fontSize,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

        Spacer(
            modifier = Modifier
                .weight(1f)
        )

        Box(
            modifier = Modifier
                .height(32.dp)
                .fillMaxWidth(0.8f)
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
                ),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = "Upgrade Now!",
                textAlign = TextAlign.Center,
                color = Color.White
            )
        }

        Spacer(
            modifier = Modifier
                .height(16.dp)
        )
    }
}
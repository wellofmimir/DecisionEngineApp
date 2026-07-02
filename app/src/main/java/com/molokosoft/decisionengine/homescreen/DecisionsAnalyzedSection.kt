package com.molokosoft.decisionengine.homescreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.size
import androidx.compose.ui.draw.shadow
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.molokosoft.decisionengine.theme.DecisionBlueLight
import com.molokosoft.decisionengine.theme.LocalAppTypography


@Composable
fun DecisionsAnalyzedSection(
    modifier: Modifier = Modifier,
    decisionsAnalyzed: Int
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
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            painterResource(id = com.molokosoft.decisionengine.R.drawable.diagnosis_foreground),
            contentDescription = null,
            modifier = Modifier
                .size(48.dp, 48.dp)
                .weight(1f)
        )

        Column(
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = decisionsAnalyzed.toString(),
                fontSize = typography.titleLarge.fontSize,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Text(
                text = "Decisions Analyzed",
                fontSize = typography.titleSmall.fontSize,
                fontWeight = FontWeight.Normal,
                color = Color.Black,
                lineHeight = 2.sp
            )

            Text(
                text = "Keep Going!",
                fontSize = typography.titleMedium.fontSize,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
    }
}
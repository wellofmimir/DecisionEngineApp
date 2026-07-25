package com.molokosoft.decisionengine.homescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.molokosoft.decisionengine.theme.LocalAppTypography

@Composable
fun JourneyCard(
    modifier: Modifier = Modifier
) {
    val typography = LocalAppTypography.current

    Column(
        modifier = modifier
            .aspectRatio(1f)
            .background(
                color = Color.White
            ),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            painterResource(id = com.molokosoft.decisionengine.R.drawable.diagnosis_foreground),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 16.dp)
                .size(32.dp, 32.dp)
        )

        Spacer(
            modifier = Modifier
                .weight(1f)
        )

        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = "12",
                fontSize = typography.titleSmall.fontSize,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Text(
                text = "Decisions Made",
                fontSize = typography.titleSmall.fontSize,
                fontWeight = FontWeight.Normal,
                color = Color.Black,
                lineHeight = 2.sp
            )

            Text(
                text = "Keep Going!",
                fontSize = typography.titleSmall.fontSize,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

        Spacer(
            modifier = Modifier
                .height(8.dp)
        )
    }
}
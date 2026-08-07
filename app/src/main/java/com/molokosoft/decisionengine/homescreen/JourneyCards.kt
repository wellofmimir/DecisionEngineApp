package com.molokosoft.decisionengine.homescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.molokosoft.decisionengine.homescreen.viewmodel.model.Statistic
import com.molokosoft.decisionengine.theme.LocalAppTypography

@Composable
fun JourneyCard(
    modifier: Modifier = Modifier,
    statistic: Statistic
) {
    val typography = LocalAppTypography.current

    Column(
        modifier = modifier
            .aspectRatio(1f)
            .background(
                color = Color.White
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            painterResource(id = statistic.icon),
            contentDescription = null,
            modifier = Modifier
                .size(32.dp, 32.dp)
        )

        Text(
            text = statistic.value,
            fontSize = typography.titleSmall.fontSize,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = statistic.title,
            fontSize = typography.titleSmall.fontSize,
            fontWeight = FontWeight.Normal,
            color = Color.Black,
            textAlign = TextAlign.Center,
            maxLines = 2,
            lineHeight = 16.sp
        )
    }
}
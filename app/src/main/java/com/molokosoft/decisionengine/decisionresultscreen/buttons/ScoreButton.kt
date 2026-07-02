package com.molokosoft.decisionengine.decisionresultscreen.buttons

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.molokosoft.decisionengine.R
import com.molokosoft.decisionengine.homescreen.buttons.getLogo
import com.molokosoft.decisionengine.theme.DecisionBlueLight
import com.molokosoft.decisionengine.theme.LocalAppTypography

@Composable
fun ScoreButton(
    modifier: Modifier = Modifier,
    decisionButtonLogo: Int,
    text: String
){
    val typography = LocalAppTypography.current

    Row(
        modifier = modifier
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(12.dp)
            )
            .height(64.dp)
            .fillMaxWidth()
            .background(
                color = DecisionBlueLight,
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                width = 1.dp,
                color = DecisionBlueLight,
                shape = RoundedCornerShape(12.dp)
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Image(
            painter = painterResource(id = decisionButtonLogo),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp, 40.dp)
                .weight(1f)
        )

        Column (
            modifier = Modifier
                .weight(3f)
        ) {
            Text(
                text = text,
                fontSize = typography.labelLarge.fontSize,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

        Image(
            painter = painterResource(id = R.drawable.pfeil_foreground),
            contentDescription = null,
            modifier = Modifier
                .weight(1f)
                .size(16.dp, 16.dp)
        )
    }
}
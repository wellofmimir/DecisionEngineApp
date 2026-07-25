package com.molokosoft.decisionengine.homescreen.buttons

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.molokosoft.decisionengine.theme.DecisionBlueLight
import com.molokosoft.decisionengine.theme.LocalAppTypography

@Composable
fun NewDecisionButton(
    modifier: Modifier = Modifier,
    onClicked: () -> Unit
) {
    val typography = LocalAppTypography.current

    Row(
        modifier = modifier
            .height(100.dp)
            .fillMaxWidth()
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(12.dp)
            )
            .background(
                DecisionBlueLight,
                RoundedCornerShape(12.dp)
            )
            .border(
                1.dp,
                Color.Transparent,
                RoundedCornerShape(12.dp)
            )
            .clickable() {
                onClicked()
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Spacer(
            modifier = Modifier
                .width(8.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.analysis_foreground),
            contentDescription = null,
            modifier = Modifier
                .size(86.dp, 86.dp)
        )

        Spacer(
            modifier = Modifier
                .width(8.dp)
        )

        Column (
            modifier = Modifier
                .weight(3f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "New Decision",
                fontSize = typography.labelLarge.fontSize,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                lineHeight = 8.sp
            )

            Spacer(
                modifier = Modifier
                    .height(8.dp)
            )

            Text(
                text = "Start a new decision analysis\nand get clear, data-driven insights.",
                fontSize = typography.labelMedium.fontSize,
                fontWeight = FontWeight.Light,
                color = Color.Black,
                lineHeight = 16.sp
            )

        }

        Image(
            painter = painterResource(id = R.drawable.pfeil_foreground),
            contentDescription = null,
            modifier = Modifier
                .size(16.dp, 16.dp)
        )

        Spacer(
            modifier = Modifier
                .width(32.dp)
        )
    }
}

package com.molokosoft.decisionengine.commonuielements

import com.molokosoft.decisionengine.theme.LocalAppTypography
import com.molokosoft.decisionengine.R

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.annotation.DrawableRes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

import com.molokosoft.decisionengine.theme.MostChosenGreen


@Composable
fun DecisionButton(
    modifier: Modifier = Modifier,
    decisionTitle: String,
    amountOfOptionsText: String,
    score: String,
    @DrawableRes decisionButtonLogo: Int,
    color: Color,
    onClicked: () -> Unit
) {
    val typography = LocalAppTypography.current

    Row(
        modifier = modifier
            .background(
                color = Color.White
            )
            .clickable() {
                onClicked()
            }
            .fillMaxWidth()
            .height(64.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
        Box(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .size(64.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = color,
                        shape = RoundedCornerShape(6.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = decisionButtonLogo),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        Row(
            modifier = Modifier
                .padding(bottom = 2.dp, end = 16.dp, top = 4.dp)
                .weight(1f),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top
        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(3f),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ){
                Text(
                    text = decisionTitle,
                    textAlign = TextAlign.Left,
                    fontSize = typography.titleSmall.fontSize,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    lineHeight = 12.sp,
                    softWrap = true,
                    overflow = TextOverflow.Clip,
                    modifier = Modifier
                        .fillMaxWidth(0.75f)
                )

                Text(
                    text = "$amountOfOptionsText options • Confidence: $score%",
                    textAlign = TextAlign.Left,
                    fontSize = typography.titleSmall.fontSize * 0.9f,
                    fontWeight = FontWeight.Light,
                    color = Color.Black
                )
            }
        }

        Spacer(
            modifier = Modifier
                .width(8.dp)
        )

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
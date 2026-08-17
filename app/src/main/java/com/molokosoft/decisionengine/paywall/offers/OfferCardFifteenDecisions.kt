package com.molokosoft.decisionengine.paywall.offers

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
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
import com.molokosoft.decisionengine.theme.DecisionBlue
import com.molokosoft.decisionengine.theme.DecisionBlueLight
import com.molokosoft.decisionengine.theme.LocalAppTypography

@Composable
fun OfferCardFifteenDecisions(
    modifier: Modifier = Modifier,
    price: String,
    offerText: String,
    offerMarketingText: String
) {
    val typography = LocalAppTypography.current

    Box(
        modifier = modifier
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
            )
            .height(128.dp)
            .fillMaxWidth(0.9f),
        contentAlignment = Alignment.CenterStart
    ){
        Spacer(
            modifier = Modifier
                .width(16.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Spacer(
                modifier = Modifier
                    .width(16.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxHeight(0.75f)
                    .aspectRatio(1f)
                    .background(
                        color = DecisionBlue,
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.analysis_foreground),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                )
            }

            Spacer(
                modifier = Modifier
                    .width(8.dp)
            )

            Column {
                Spacer(
                    modifier = Modifier
                        .height(1.dp)
                        .weight(1f)
                )

                Text(
                    text = price,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontSize = typography.titleLarge.fontSize,
                    lineHeight = 8.sp
                )

                Text(
                    text = offerText,
                    fontWeight = FontWeight.Light,
                    color = Color.Black,
                    lineHeight = 8.sp
                )

                Text(
                    text = offerMarketingText,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    lineHeight = 16.sp
                )

                Spacer(
                    modifier = Modifier
                        .height(1.dp)
                        .weight(1f)
                )
            }

            Spacer(
                modifier = Modifier
                    .weight(1f)
            )

            Image(
                painter = painterResource(id = R.drawable.pfeil_foreground),
                contentDescription = null,
                modifier = Modifier
                    .size(16.dp, 16.dp)
            )

            Spacer(
                modifier = Modifier
                    .width(16.dp)
            )
        }
    }
}
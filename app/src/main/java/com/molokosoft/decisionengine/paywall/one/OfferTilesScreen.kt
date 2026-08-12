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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.molokosoft.decisionengine.R
import com.molokosoft.decisionengine.billing.model.SubscriptionProduct
import com.molokosoft.decisionengine.theme.DecisionBlue
import com.molokosoft.decisionengine.theme.DecisionBlueLight
import com.molokosoft.decisionengine.theme.DecisionGreen
import com.molokosoft.decisionengine.theme.LocalAppTypography
import com.molokosoft.decisionengine.theme.FreeTrialPink
import com.molokosoft.decisionengine.theme.MostChosenGreen
import com.molokosoft.decisionengine.commonclasses.SubscriptionTypes

@Composable
fun OfferTilesScreen(
    modifier: Modifier = Modifier,
    subscriptionProducts: List<SubscriptionProduct>,
    onContinueClicked: (offerType: SubscriptionTypes) -> Unit
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
            modifier = Modifier.padding(top = 8.dp)
                .fillMaxWidth(0.5f)
                .aspectRatio(1f)
        )

        Spacer(
            modifier = Modifier
                .height(16.dp)
        )

        Text(
            text = "DecisionEngine",
            textAlign = TextAlign.Center,
            fontSize = typography.titleLarge.fontSize * 1.5f,
            color = Color.Black
        )

        Spacer(
            modifier = Modifier
                .height(8.dp)
        )

        Text(
            text = "We handle the analysis",
            textAlign = TextAlign.Center,
            fontSize = typography.titleMedium.fontSize,
            color = Color.Black
        )

        Text(
            text = buildAnnotatedString {
                append("so you can ")

                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    append("focus on action.")
                }
            },
            textAlign = TextAlign.Center,
            fontSize = typography.titleMedium.fontSize,
            color = Color.Black
        )

        Spacer(
            modifier = Modifier
                .height(16.dp)
        )

        Box(
            modifier = Modifier
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
                .fillMaxWidth(0.9f)
                .clickable(){
                    onContinueClicked(SubscriptionTypes.Weekly)
                },
            contentAlignment = Alignment.CenterStart
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
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
                ){
                    Image(
                        painter = painterResource(id = R.drawable.calendardsymbol_foreground),
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
                        text = subscriptionProducts.find {
                            it.productId == "decisionengine_weekly_subscription"
                        }?.formattedPrice ?: "$0.89",
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        fontSize = typography.titleLarge.fontSize,
                        lineHeight = 8.sp
                    )

                    Text(
                        text = "Weekly billing.",
                        fontWeight = FontWeight.Light,
                        color = Color.Black,
                        lineHeight = 8.sp
                    )

                    Text(
                        text = "No long-term commitment.",
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black,
                        lineHeight = 8.sp
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

        Spacer(
            modifier = Modifier
                .height(4.dp)
        )

        Box(
            modifier = Modifier
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
                .fillMaxWidth(0.9f)
                .clickable {
                    onContinueClicked(SubscriptionTypes.Yearly)
                },
            contentAlignment = Alignment.CenterStart
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                Spacer(
                    modifier = Modifier
                        .width(16.dp)
                )

                Box(
                    modifier = Modifier
                        .fillMaxHeight(0.75f)
                        .aspectRatio(1f)
                        .background(
                            color = DecisionGreen.copy(alpha = 0.7f),
                            shape = RoundedCornerShape(12.dp)
                        ),
                    contentAlignment = Alignment.Center
                ){
                    Image(
                        painter = painterResource(id = R.drawable.calendardsymbol_foreground),
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxWidth(0.5f)
                    )

                    Image(
                        painter = painterResource(id = R.drawable.star_blue_foreground),
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .fillMaxWidth(0.45f)
                            .background(
                                shape = CircleShape,
                                color = MostChosenGreen
                            )
                            .padding(8.dp)
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
                        text = subscriptionProducts.find {
                            it.productId == "yearly_subscription"
                        }?.formattedPrice ?: "$24.99",
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        fontSize = typography.titleLarge.fontSize,
                        lineHeight = 8.sp
                    )

                    Text(
                        text = "Yearly billing.",
                        fontWeight = FontWeight.Light,
                        color = Color.Black,
                        lineHeight = 8.sp
                    )

                    Text(
                        text = "Save over 30%.",
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black,
                        lineHeight = 8.sp
                    )

                    Box(
                        modifier = Modifier
                            .background(
                                color = MostChosenGreen,
                                shape = RoundedCornerShape(2.dp)
                            )
                            .padding(2.dp)
                            .align(Alignment.CenterHorizontally)

                    ){
                        Text(
                            text = "Most chosen offer",
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black,
                            lineHeight = 8.sp,
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                        )
                    }

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

        Spacer(
            modifier = Modifier
                .height(4.dp)
        )

        Box(
            modifier = Modifier
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
                .fillMaxWidth(0.9f)
                .clickable {
                    onContinueClicked(SubscriptionTypes.FreeTrial)
                },
            contentAlignment = Alignment.CenterStart
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                Spacer(
                    modifier = Modifier
                        .width(16.dp)
                )

                Box(
                    modifier = Modifier
                        .fillMaxHeight(0.75f)
                        .aspectRatio(1f)
                        .background(
                            color = FreeTrialPink,
                            shape = RoundedCornerShape(12.dp)
                        ),
                    contentAlignment = Alignment.Center
                ){
                    Image(
                        painter = painterResource(id = R.drawable.padlock_foreground),
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
                        text = "Free Trial",
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        fontSize = typography.titleLarge.fontSize,
                        lineHeight = 8.sp
                    )

                    Text(
                        text = "Full Premium Access.",
                        fontWeight = FontWeight.Light,
                        color = Color.Black,
                        lineHeight = 8.sp
                    )

                    Text(
                        text = "We'll remind you\nbefore your trial ends.",
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black,
                        lineHeight = 13.sp
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

        Spacer(
            modifier = Modifier
                .weight(1f)
        )
    }
}
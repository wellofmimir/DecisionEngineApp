package com.molokosoft.decisionengine.paywall.two

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
import androidx.compose.ui.text.style.TextDecoration
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
import com.molokosoft.decisionengine.commonclasses.ProductTypes
import com.molokosoft.decisionengine.paywall.offers.OfferCardFifteenDecisions
import com.molokosoft.decisionengine.paywall.offers.OfferCardWeeklySubscription

@Composable
fun OfferTilesScreen(
    modifier: Modifier = Modifier,
    productInformation: List<SubscriptionProduct>,
    onContinueClicked: (offerType: ProductTypes) -> Unit,
    onBackClicked: () -> Unit,
    onRestore: () -> Unit,
    showBackButton: Boolean = false,
    showRestorageButton: Boolean = false
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
                .height(48.dp)
        )

        Column{
            OfferCardWeeklySubscription(
                price = productInformation.find {
                    it.productId == ProductTypes.Weekly.value
                }?.formattedPrice ?: "",
                offerText = "Weekly billing.",
                offerMarketingText = "20 analyses per week.",
                modifier = Modifier
                    .clickable {
                        onContinueClicked(ProductTypes.Weekly)
                    }
            )

            Spacer(
                modifier = Modifier
                    .height(4.dp)
            )

            OfferCardFifteenDecisions(
                price = productInformation.find {
                    it.productId == ProductTypes.Usages15.value
                }?.formattedPrice ?: "",
                offerText = "One-time purchase.",
                offerMarketingText = "15 analyses.\nNo expiration.",
                modifier = Modifier
                    .clickable {
                        onContinueClicked(ProductTypes.Usages15)
                    }
            )
        }

        if (showRestorageButton) {
            Spacer(
                modifier = Modifier
                    .height(32.dp)
            )

            Text(
                text = "I have a Restore Key",
                textAlign = TextAlign.Center,
                color = Color.Black,
                fontSize = typography.titleSmall.fontSize,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .clickable {
                        onRestore()
                    }
            )
        }

        Spacer(
            modifier = Modifier
                .weight(1f)
        )

        if (showBackButton) {
            Text(
                text = "Back",
                textAlign = TextAlign.Center,
                color = Color.Black,
                fontSize = typography.titleSmall.fontSize,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .clickable {
                        onBackClicked()
                    }
            )

            Spacer(
                modifier = Modifier
                    .height(16.dp)
            )
        }
    }
}
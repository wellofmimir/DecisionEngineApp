package com.molokosoft.decisionengine.newdecisionscreen.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.molokosoft.decisionengine.R
import com.molokosoft.decisionengine.billing.model.SubscriptionProduct
import com.molokosoft.decisionengine.commonclasses.ProductTypes
import com.molokosoft.decisionengine.paywall.offers.OfferCardFifteenDecisions
import com.molokosoft.decisionengine.theme.LocalAppTypography

@Composable
fun NoMoreUsagesScreen(
    modifier: Modifier = Modifier,
    productInformation: List<SubscriptionProduct>,
    onAccepted: () -> Unit,
    onDeclined: () -> Unit
) {
    val typography = LocalAppTypography.current
    val verticalScroll = rememberScrollState()

    Column(
        modifier = modifier
            .verticalScroll(verticalScroll)
            .background(
                color = Color.White
            )
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            modifier = Modifier
                .height(64.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.nomoreusages),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .aspectRatio(1f)
        )

        Text(
            text = "No Usages Left",
            textAlign = TextAlign.Center,
            fontSize = typography.titleMedium.fontSize,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        )

        Spacer(
            modifier = Modifier
                .height(16.dp)
        )

        Text(
            text = "You've used all your available analyses.",
            textAlign = TextAlign.Center,
            fontSize = typography.titleSmall.fontSize,
            color = Color.Black
        )

        Spacer(
            modifier = Modifier
                .height(16.dp)
        )

        OfferCardFifteenDecisions(
            price = productInformation.find {
                it.productId == ProductTypes.Usages15.value
            }?.formattedPrice ?: "",
            offerText = "One-time purchase.",
            offerMarketingText = "15 analyses.\nNo expiration.",
            modifier = Modifier
                .clickable {
                    onAccepted()
                }
        )

        Spacer(
            modifier = Modifier
                .weight(1f)
        )

        Spacer(
            modifier = Modifier
                .height(16.dp)
        )

        Text(
            text = "Back",
            textAlign = TextAlign.Center,
            color = Color.Black,
            fontSize = typography.titleSmall.fontSize,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier
                .clickable() {
                    onDeclined()
                }
        )

        Spacer(
            modifier = Modifier
                .height(16.dp)
        )
    }
}
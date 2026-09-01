package com.molokosoft.decisionengine.paywall.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.text.buildAnnotatedString
import com.molokosoft.decisionengine.R
import com.molokosoft.decisionengine.commonclasses.ProductTypes
import com.molokosoft.decisionengine.theme.DecisionBlue
import com.molokosoft.decisionengine.theme.LocalAppTypography
import kotlinx.coroutines.delay
import androidx.compose.ui.text.SpanStyle

@Composable
fun SubscriptionInformationDialog(
    modifier: Modifier = Modifier,
    subscriptionType: ProductTypes,
    price: String,
    onDismissRequest: () -> Unit,
    onAcceptedOffer: () -> Unit
) {
    val title = when (subscriptionType) {
        ProductTypes.Weekly ->
            "Weekly Plan"

        ProductTypes.Yearly ->
            "Yearly Plan"

        ProductTypes.Usages15 ->
            "One Time Purchase"

        else ->
            ""
    }

    var isButtonActive by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(isButtonActive) {
        if (!isButtonActive) {
            delay(10000)
            isButtonActive = true
        }
    }

    val subTitle = when (subscriptionType) {
        ProductTypes.Weekly ->
            "Get 20 decision analyses every week with the Weekly subscription plan.\n\n" +
            "This plan is ideal if you use Decision Engine regularly and prefer a flexible subscription with a short billing period."

        ProductTypes.Usages15 ->
            "Get 15 decision analyses with no expiration." +
            "This offer is ideal for occasional users who don't need a subscription and want the flexibility to use their decision analyses whenever they need them."

        else -> ""
    }

    val howManyDecisionsText = when (subscriptionType) {
        ProductTypes.Weekly ->
            "You can analyze up to 20 decisions per week."

        ProductTypes.Usages15 ->
            "You can analyze 15 decisions. No expiration."

        else ->
            ""
    }

    val whenDoDecisionsResetText = when (subscriptionType) {
        ProductTypes.Weekly ->
            "Your 20 decisions are renewed at the beginning of each new weekly subscription period."

        ProductTypes.Usages15 ->
            "Your 15 decisions never expire and do not reset. They remain available until you use them."

        else ->
            ""
    }

    val carryOverTitle = when (subscriptionType) {
        ProductTypes.Weekly ->
            "Do unused decisions carry over?"

        ProductTypes.Usages15 ->
            "Do usages expire?"

        else ->
            ""
    }

    val carryOverText = when (subscriptionType) {
        ProductTypes.Weekly ->
            "No. Unused decisions do not carry over to the next subscription period."

        ProductTypes.Usages15 ->
            "Usages do not expire."

        else ->
            ""
    }

    val cancelText = when (subscriptionType) {
        ProductTypes.Weekly ->
            "Yes. You can cancel your subscription at any time. You will continue to have access to your subscription benefits until the end of the current subscription period."

        ProductTypes.Usages15 ->
            "There is no expiration date. You can use your 15 decision analyses whenever you need them."

        else ->
            ""
    }

    val whoForTitleText = when (subscriptionType) {
        ProductTypes.Weekly ->
            "Who is the Weekly plan for?"

        ProductTypes.Usages15 ->
            "Who is this offer for?"

        else ->
            ""
    }

    val whoForText = when (subscriptionType) {
        ProductTypes.Weekly ->
            "The Weekly plan is a good choice if you want to use Decision Engine regularly."

        ProductTypes.Usages15 ->
            "This offer is a good choice if you use Decision Engine occasionally and want the flexibility to use your 15 decision analyses whenever you need them, without a subscription."

        else ->
            ""
    }

    val priceTitle =
        "How much does it cost?"

    val priceText = if (price.isBlank()) {
        """
        Sorry, the price information is currently unavailable\n.
        Tap “Accept” to continue to Google Play, where the current price will be displayed before you complete the purchase.\n
        You will not be charged until you confirm the purchase in Google Play.\n
    """.trimIndent()
    } else {
        val temporary = buildAnnotatedString {
            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                append(price)
            }

            append(
                when (subscriptionType) {
                    ProductTypes.Weekly -> " per week."
                    ProductTypes.Usages15 -> " once."
                    else -> ""
                }
            )
        }

        temporary
    }

    val typography = LocalAppTypography.current
    val verticalScroll = rememberScrollState()

    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Column(
            modifier = modifier
                .verticalScroll(verticalScroll)
                .fillMaxWidth()
                .background(
                    color = Color.White,
                    RoundedCornerShape(12.dp)
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(
                modifier = Modifier
                    .height(16.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.decisionenginelogonew),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .aspectRatio(1f)
            )

            Spacer(
                modifier = Modifier
                    .height(16.dp)
            )

            Text(
                text = title,
                textAlign = TextAlign.Center,
                fontSize = typography.titleMedium.fontSize,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline
            )

            Spacer(
                modifier = Modifier
                    .height(8.dp)
            )

            Text(
                text = subTitle,
                textAlign = TextAlign.Center,
                fontSize = typography.titleSmall.fontSize,
                color = Color.Black,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .padding(horizontal = 4.dp)
            )

            Spacer(
                modifier = Modifier
                    .height(16.dp)
            )

            Text(
                text = "How many decisions are included?",
                textAlign = TextAlign.Center,
                fontSize = typography.titleSmall.fontSize,
                color = Color.Black,
                fontWeight = FontWeight.SemiBold
            )

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth(0.8f),
                color = DecisionBlue
            )

            Text(
                text = howManyDecisionsText,
                textAlign = TextAlign.Center,
                fontSize = typography.titleSmall.fontSize,
                color = Color.Black,
                fontWeight = FontWeight.Light,
                modifier = Modifier
                    .padding(horizontal = 4.dp)
            )

            Spacer(
                modifier = Modifier
                    .height(16.dp)
            )

            Text(
                text = "When do my decisions reset?",
                textAlign = TextAlign.Center,
                fontSize = typography.titleSmall.fontSize,
                color = Color.Black,
                fontWeight = FontWeight.SemiBold
            )

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth(0.8f),
                color = DecisionBlue
            )

            Text(
                text = whenDoDecisionsResetText,
                textAlign = TextAlign.Center,
                fontSize = typography.titleSmall.fontSize,
                color = Color.Black,
                fontWeight = FontWeight.Light,
                modifier = Modifier
                    .padding(horizontal = 4.dp)
            )

            Spacer(
                modifier = Modifier
                    .height(16.dp)
            )

            Text(
                text = carryOverTitle,
                textAlign = TextAlign.Center,
                fontSize = typography.titleSmall.fontSize,
                color = Color.Black,
                fontWeight = FontWeight.SemiBold
            )

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth(0.8f),
                color = DecisionBlue
            )

            Text(
                text = carryOverText,
                textAlign = TextAlign.Center,
                fontSize = typography.titleSmall.fontSize,
                color = Color.Black,
                fontWeight = FontWeight.Light,
                modifier = Modifier
                    .padding(horizontal = 4.dp)
            )

            Spacer(
                modifier = Modifier
                    .height(16.dp)
            )

            Text(
                text = "Can I cancel my subscription?",
                textAlign = TextAlign.Center,
                fontSize = typography.titleSmall.fontSize,
                color = Color.Black,
                fontWeight = FontWeight.SemiBold
            )

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth(0.8f),
                color = DecisionBlue
            )

            Text(
                text = cancelText,
                textAlign = TextAlign.Center,
                fontSize = typography.titleSmall.fontSize,
                color = Color.Black,
                fontWeight = FontWeight.Light,
                modifier = Modifier
                    .padding(horizontal = 4.dp)
            )

            Spacer(
                modifier = Modifier
                    .height(16.dp)
            )

            Text(
                text = whoForTitleText,
                textAlign = TextAlign.Center,
                fontSize = typography.titleSmall.fontSize,
                color = Color.Black,
                fontWeight = FontWeight.SemiBold
            )

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth(0.8f),
                color = DecisionBlue
            )

            Text(
                text = whoForText,
                textAlign = TextAlign.Center,
                fontSize = typography.titleSmall.fontSize,
                color = Color.Black,
                fontWeight = FontWeight.Light,
                modifier = Modifier
                    .padding(horizontal = 4.dp)
            )

            Spacer(
                modifier = Modifier
                    .height(16.dp)
            )

            Text(
                text = priceTitle,
                textAlign = TextAlign.Center,
                fontSize = typography.titleSmall.fontSize,
                color = Color.Black,
                fontWeight = FontWeight.SemiBold
            )

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth(0.8f),
                color = DecisionBlue
            )

            Text(
                text = priceText.toString(),
                textAlign = TextAlign.Center,
                fontSize = typography.titleSmall.fontSize,
                color = Color.Black,
                fontWeight = FontWeight.Light,
                modifier = Modifier
                    .padding(horizontal = 4.dp)
            )

            Spacer(
                modifier = Modifier
                    .weight(1f)
            )

            Spacer(
                modifier = Modifier
                    .height(16.dp)
            )

            Box(
                modifier = Modifier
                    .height(64.dp)
                    .fillMaxWidth(0.75f)
                    .border(
                        width = 1.dp,
                        color = if (isButtonActive) DecisionBlue else Color.Gray,
                        shape = RoundedCornerShape(64.dp)
                    )
                    .background(
                        color = if (isButtonActive) DecisionBlue else Color.Gray,
                        shape = RoundedCornerShape(64.dp)
                    )
                    .clickable(){
                        if (isButtonActive) {
                            onAcceptedOffer()
                            isButtonActive = false
                        }
                    },
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = "Accept",
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
            }

            Spacer(
                modifier = Modifier
                    .height(16.dp)
            )

            Text(
                text = "Close",
                textAlign = TextAlign.Center,
                color = Color.Black,
                fontSize = typography.titleSmall.fontSize,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .clickable() {
                        onDismissRequest()
                    }
            )

            Spacer(
                modifier = Modifier
                    .height(16.dp)
            )
        }
    }
}
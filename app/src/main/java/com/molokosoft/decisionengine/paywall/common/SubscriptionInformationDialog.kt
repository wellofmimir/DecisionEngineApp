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
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import com.molokosoft.decisionengine.R
import com.molokosoft.decisionengine.commonclasses.SubscriptionTypes
import com.molokosoft.decisionengine.theme.DecisionBlue
import com.molokosoft.decisionengine.theme.LocalAppTypography

@Composable
fun SubscriptionInformationDialog(
    modifier: Modifier = Modifier,
    subscriptionType: SubscriptionTypes,
    onDismissRequest: () -> Unit,
    onAcceptedOffer: () -> Unit
) {
    val title = when (subscriptionType) {
        SubscriptionTypes.Weekly ->
            "Weekly Plan"

        SubscriptionTypes.Yearly ->
            "Yearly Plan"

        else ->
            ""
    }

    val subTitle = when (subscriptionType) {
        SubscriptionTypes.Weekly ->
            "Get 100 decision analyses every week with the Weekly subscription plan.\n\n" +
            "This plan is ideal if you use Decision Engine regularly and prefer a flexible subscription with a short billing period."

        SubscriptionTypes.Yearly ->
            "Get 5000 decision analyses every week with the Yearly subscription plan.\n\n" +
            "This plan is ideal for frequent users who want a large decision allowance and the convenience of a yearly subscription."

        else -> ""
    }

    val howManyDecisionsText = when (subscriptionType) {
        SubscriptionTypes.Weekly ->
            "You can analyze up to 100 decisions per week."

        SubscriptionTypes.Yearly ->
            "You can analyze up to 5,000 decisions per year."

        else ->
            ""
    }

    val whenDoDecisionsResetText = when (subscriptionType) {
        SubscriptionTypes.Weekly ->
            "Your 100 decisions are renewed at the beginning of each new weekly subscription period."

        SubscriptionTypes.Yearly ->
            "Your 5,000 decisions are renewed at the beginning of each new yearly subscription period."

        else ->
            ""
    }

    val carryOverText =
        "No. Unused decisions do not carry over to the next subscription period."

    val cancelText =
        "Yes. You can cancel your subscription at any time. You will continue to have access to your subscription benefits until the end of the current subscription period."

    val whoForText = when (subscriptionType) {
        SubscriptionTypes.Weekly ->
            "The Weekly plan is a good choice if you want to use Decision Engine regularly without committing to a yearly subscription."

        SubscriptionTypes.Yearly ->
            "The Yearly plan is a good choice if you use Decision Engine frequently and want a generous decision allowance without having to renew your subscription every week."

        else ->
            ""
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
                text = "Do unused decisions carry over?",
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
                text = "Who is the Yearly plan for?",
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
                        color = DecisionBlue,
                        shape = RoundedCornerShape(64.dp)
                    )
                    .background(
                        color = DecisionBlue,
                        shape = RoundedCornerShape(64.dp)
                    )
                    .clickable(){
                        onAcceptedOffer()
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
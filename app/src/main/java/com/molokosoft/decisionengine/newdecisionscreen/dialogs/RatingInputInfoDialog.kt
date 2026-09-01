package com.molokosoft.decisionengine.newdecisionscreen.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.molokosoft.decisionengine.theme.DecisionBlue
import com.molokosoft.decisionengine.theme.LocalAppTypography

@Composable
fun CriteriaRatingInfoDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit
) {
    val typography =
        LocalAppTypography.current

    val verticalScroll =
        rememberScrollState()

    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Column(
            modifier = modifier
                .verticalScroll(verticalScroll)
                .fillMaxWidth()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(12.dp)
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(
                modifier = Modifier
                    .height(16.dp)
            )

            Text(
                text = "How should I rate the criteria?",
                textAlign = TextAlign.Center,
                fontSize = typography.titleMedium.fontSize,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            )

            Spacer(
                modifier = Modifier
                    .height(12.dp)
            )

            Text(
                text = "Rate how well each option meets the criterion.",
                textAlign = TextAlign.Center,
                fontSize = typography.titleSmall.fontSize,
                color = Color.Black,
                fontWeight = FontWeight.Light,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            )

            Spacer(
                modifier = Modifier
                    .height(16.dp)
            )

            CriteriaRatingExample(
                criterion = "Price",
                option = "Toyota Corolla",
                rating = "Very Good: 10"
            )

            CriteriaRatingExample(
                criterion = "Price",
                option = "Honda Civic",
                rating = "Not Good: 1"
            )

            Spacer(
                modifier = Modifier
                    .height(4.dp)
            )

            Text(
                text = "For example, if price is an important criterion, a car that fits your budget should receive a higher rating than an expensive car.",
                textAlign = TextAlign.Center,
                fontSize = typography.titleSmall.fontSize,
                color = Color.Black,
                fontWeight = FontWeight.Light,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            )

            Spacer(
                modifier = Modifier
                    .height(12.dp)
            )

            Text(
                text = "Rate each option based on how well it meets your needs and expectations.",
                textAlign = TextAlign.Center,
                fontSize = typography.titleSmall.fontSize,
                color = Color.Black,
                fontWeight = FontWeight.Light,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
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
                    .clickable() {
                        onDismissRequest()
                    },
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = "Got It",
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
            }

            Spacer(
                modifier = Modifier
                    .height(16.dp)
            )
        }
    }
}

@Composable
private fun CriteriaRatingExample(
    criterion: String,
    option: String,
    rating: String
) {
    val typography = LocalAppTypography.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = criterion,
            textAlign = TextAlign.Center,
            fontSize = typography.titleSmall.fontSize,
            color = DecisionBlue,
            fontWeight = FontWeight.SemiBold
        )

        Text(
            text = option,
            textAlign = TextAlign.Center,
            fontSize = typography.titleSmall.fontSize,
            color = Color.Black,
            fontWeight = FontWeight.Medium
        )

        Text(
            text = rating,
            textAlign = TextAlign.Center,
            fontSize = typography.titleSmall.fontSize,
            color = Color.Black,
            fontWeight = FontWeight.Light
        )

        Spacer(
            modifier = Modifier
                .height(12.dp)
        )

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth(0.8f),
            color = DecisionBlue
        )

        Spacer(
            modifier = Modifier
                .height(12.dp)
        )
    }
}
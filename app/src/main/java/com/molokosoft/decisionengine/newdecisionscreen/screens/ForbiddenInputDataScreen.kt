package com.molokosoft.decisionengine.newdecisionscreen.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.molokosoft.decisionengine.R
import com.molokosoft.decisionengine.theme.LocalAppTypography

@Composable
fun ForbiddenRequestCard(
    modifier: Modifier = Modifier,
){
    val typography = LocalAppTypography.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = Color.Red.copy(0.25f),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(top = 16.dp, bottom = 16.dp)
    ){
        Text(
            text = "This includes data that is:",
            textAlign = TextAlign.Left,
            fontSize = typography.titleMedium.fontSize,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
            modifier = Modifier
                .padding(start = 16.dp, bottom = 16.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 8.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Image(
                painter = painterResource(id = R.drawable.notallowed),
                contentDescription = null,
                modifier = Modifier
                    .weight(1f)
                    .size(64.dp)
            )

            Column(
                modifier = Modifier
                    .weight(2f),
                horizontalAlignment = Alignment.Start
            ){
                Text(
                    text = "X Instructions that try to manipulate the AI",
                    textAlign = TextAlign.Start,
                    fontSize = typography.titleSmall.fontSize,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )

                Text(
                    text = "X Attempts to bypass system rules",
                    textAlign = TextAlign.Start,
                    fontSize = typography.titleSmall.fontSize,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )

                Text(
                    text = "X Prompt injection or jailbreak attempts",
                    textAlign = TextAlign.Start,
                    fontSize = typography.titleSmall.fontSize,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )

                Text(
                    text = "X Attempts to access internal instructions",
                    textAlign = TextAlign.Start,
                    fontSize = typography.titleSmall.fontSize,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )
            }
        }
    }
}

@Composable
fun ForbiddenInputDataScreen(
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit
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
            painter = painterResource(id = R.drawable.notallowed),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .aspectRatio(1f)
        )

        Text(
            text = "We cannot process this data.",
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
            text = "Your request is against our guidelines.\n" +
                    "Please insert data that does not try\nto mingle with our service.\n" +
                    "Thank you.",
            textAlign = TextAlign.Center,
            fontSize = typography.titleSmall.fontSize,
            color = Color.Black
        )

        Spacer(
            modifier = Modifier
                .height(16.dp)
        )

        ForbiddenRequestCard(
            modifier = Modifier
                .padding(horizontal = 8.dp)
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
package com.molokosoft.decisionengine.newdecisionscreen.screens

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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import android.content.Context
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import android.net.Uri
import android.content.Intent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.molokosoft.decisionengine.R
import com.molokosoft.decisionengine.theme.DecisionBlue
import com.molokosoft.decisionengine.theme.DecisionBlueLight
import com.molokosoft.decisionengine.theme.LocalAppTypography
import androidx.core.net.toUri
import com.molokosoft.decisionengine.theme.DecisionGreen

@Composable
fun SuicidePreventionHotline(
    modifier: Modifier = Modifier,
    onClicked: () -> Unit,
) {
    val typography = LocalAppTypography.current

    Row(
        modifier = modifier
            .border(
                width = 4.dp,
                color = Color.Red,
                shape = RoundedCornerShape(12.dp)
            )
            .background(
                color = Color.White,
                shape = RoundedCornerShape(12.dp)
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
                        color = DecisionGreen,
                        shape = RoundedCornerShape(6.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.globe_foreground),
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
                    text = "Find a Help Line",
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
                    text = "https://findahelpline.com",
                    textAlign = TextAlign.Left,
                    fontSize = typography.titleSmall.fontSize * 0.9f,
                    fontWeight = FontWeight.Light,
                    color = Color.Black,
                    lineHeight = 12.sp
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

@Composable
fun NotAllowedRequestsCard(
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
            text = "This includes decisions that are:",
            textAlign = TextAlign.Left,
            fontSize = typography.titleMedium.fontSize,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
            modifier = Modifier
                .padding(start = 16.dp, bottom = 16.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
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
                    text = "X harmful, violent or dangerous",
                    textAlign = TextAlign.Start,
                    fontSize = typography.titleSmall.fontSize,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )

                Text(
                    text = "X unethical or illegal",
                    textAlign = TextAlign.Start,
                    fontSize = typography.titleSmall.fontSize,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )

                Text(
                    text = "X self-harm or suicide related",
                    textAlign = TextAlign.Start,
                    fontSize = typography.titleSmall.fontSize,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )

                Text(
                    text = "X discriminatory or hateful",
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
fun WeCareAboutYouCard(
    modifier: Modifier = Modifier,
){
    val typography = LocalAppTypography.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = DecisionBlue.copy(0.25f),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(top = 16.dp, bottom = 16.dp)
    ){
        Text(
            text = "We care about you",
            textAlign = TextAlign.Left,
            fontSize = typography.titleMedium.fontSize,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
            modifier = Modifier
                .padding(start = 16.dp, bottom = 16.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Image(
                painter = painterResource(id = R.drawable.heart_foreground),
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
                    text = "If you're thinking about suicide,\n" +
                            "or about hurting yourself\n" +
                           "please know that help is available!",
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
fun NotAllowedScreen(
    modifier: Modifier = Modifier,
    context: Context,
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
            text = "We cannot analyze this decision.",
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
                    "DecisionEngine is designed to support\npositive, ethical and safe decision making.",
            textAlign = TextAlign.Center,
            fontSize = typography.titleSmall.fontSize,
            color = Color.Black
        )

        Spacer(
            modifier = Modifier
                .height(16.dp)
        )

        NotAllowedRequestsCard(
            modifier = Modifier
                .padding(horizontal = 8.dp)
        )

        Spacer(
            modifier = Modifier
                .height(8.dp)
        )

        WeCareAboutYouCard(
            modifier = Modifier
                .padding(horizontal = 8.dp)
        )

        Spacer(
            modifier = Modifier
                .height(8.dp)
        )

        SuicidePreventionHotline(
            modifier = Modifier
                .padding(horizontal = 8.dp),
            onClicked = {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    "https://findahelpline.com/".toUri()
                )

                context.startActivity(intent)
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
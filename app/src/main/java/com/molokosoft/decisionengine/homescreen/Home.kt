package com.molokosoft.decisionengine.homescreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color

import com.molokosoft.decisionengine.theme.LocalAppTypography
import com.molokosoft.decisionengine.homescreen.buttons.DecisionButton
import com.molokosoft.decisionengine.homescreen.buttons.DecisionButtonLogo
@Composable
fun Home(
    name: String,
    modifier: Modifier = Modifier
){
    val typography = LocalAppTypography.current

    Column(
        modifier = modifier
            .background(
                color = Color.White
            )
            .fillMaxHeight()
            .padding(all = 8.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Hello $name",
            fontSize = typography.titleLarge.fontSize,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        )

        Text(
            text = "What decision are you\nworking on today?",
            fontSize = typography.titleMedium.fontSize,
            fontWeight = FontWeight.Light,
            color = Color.Black
        )

        Spacer(
            modifier = Modifier
                .height(8.dp)
        )

        DecisionButton(
            mainText = "New Decision",
            subText = "Start a new analysis",
            decisionButtonLogo = DecisionButtonLogo.Plus
        )

        Spacer(
            modifier = Modifier
                .height(8.dp)
        )

        Text(
            text = "Recent Decisions",
            fontSize = typography.titleMedium.fontSize,
            textDecoration = TextDecoration.Underline,
            fontStyle = FontStyle.Italic,
            color = Color.Black
        )

        Spacer(
            modifier = Modifier
                .height(8.dp)
        )

        repeat(3){
            DecisionButton(
                mainText = "Job offer in Ireland",
                decisionButtonLogo = DecisionButtonLogo.Diagnosis
            )

            Spacer(
                modifier = Modifier
                    .height(4.dp)
            )
        }

        Spacer(
            modifier = Modifier
                .height(4.dp)
        )

        Text(
            text = "Decision Insights",
            fontSize = typography.titleMedium.fontSize,
            textDecoration = TextDecoration.Underline,
            fontStyle = FontStyle.Italic,
            color = Color.Black
        )

        Spacer(
            modifier = Modifier
                .height(8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
        ){
            DecisionsAnalyzedSection(
                modifier = Modifier
                    .weight(1f),
                decisionsAnalyzed = 12
            )

            Spacer(
                modifier = Modifier
                    .width(4.dp)
            )

            UpdateToProSection(
                modifier = Modifier
                    .weight(1f)
            )
        }
    }
}
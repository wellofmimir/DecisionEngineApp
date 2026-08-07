package com.molokosoft.decisionengine.homescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.molokosoft.decisionengine.theme.DecisionBlueLight
import com.molokosoft.decisionengine.theme.LocalAppTypography

@Composable
fun MotivationalQuoteBox(
    modifier: Modifier = Modifier,
    quote: String,
    person: String,
    onDismissRequest: () -> Unit
) {
    val typography = LocalAppTypography.current

    Dialog(
        onDismissRequest = {
            onDismissRequest()
        }
    ){
        Column(
            modifier = modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = DecisionBlueLight,
                    shape = RoundedCornerShape(12.dp)
                )
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(12.dp)
                )
                .background(
                    color = DecisionBlueLight,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(16.dp)
        ) {
            Text(
                text = quote,
                modifier = Modifier.fillMaxWidth(),
                fontSize = typography.titleMedium.fontSize,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Start,
                color = Color.Black
            )

            Spacer(
                modifier = Modifier
                    .height(16.dp)
            )

            Text(
                text = "- $person",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End,
                fontWeight = FontWeight.Light,
                color = Color.Black
            )
        }
    }
}
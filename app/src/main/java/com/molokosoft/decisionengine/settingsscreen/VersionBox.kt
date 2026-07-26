package com.molokosoft.decisionengine.settingsscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

import com.molokosoft.decisionengine.R
import com.molokosoft.decisionengine.theme.DecisionBlue
import com.molokosoft.decisionengine.theme.DecisionBlueLight
import com.molokosoft.decisionengine.theme.LocalAppTypography

@Composable
fun VersionBox(
    modifier: Modifier = Modifier,
    version: String,
    onDismissRequest: () -> Unit
) {
    val typography = LocalAppTypography.current

    Dialog(
        onDismissRequest = {
            onDismissRequest()
        }
    ){
        Box(
            modifier = modifier
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
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            color = DecisionBlueLight,
                            shape = RoundedCornerShape(6.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.layers_foreground),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(
                    Modifier
                        .height(16.dp)
                )

                Text(
                    text = "DecisionEngine",
                    fontSize = typography.titleMedium.fontSize,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    color = Color.Black
                )

                Spacer(
                    Modifier
                        .height(8.dp)
                )

                Text(
                    text = "Version:",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Text(
                    text = version,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )

                Spacer(
                    Modifier
                        .height(8.dp)
                )

                Text(
                    text = "Built to help you make better decisions.",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Light,
                    color = Color.Black
                )
            }
        }
    }
}
package com.molokosoft.decisionengine.settingsscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.molokosoft.decisionengine.R

import com.molokosoft.decisionengine.theme.DecisionBlue
import com.molokosoft.decisionengine.theme.DecisionBlueLight
import com.molokosoft.decisionengine.theme.DecisionGreen
import com.molokosoft.decisionengine.theme.LocalAppTypography
import com.molokosoft.decisionengine.theme.FreeTrialPink

@Composable
fun AboutSection(
    modifier: Modifier = Modifier
) {
    val typography = LocalAppTypography.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(12.dp)
            )
            .background(
                DecisionBlueLight,
                RoundedCornerShape(12.dp)
            )
            .border(
                1.dp,
                Color.Transparent,
                RoundedCornerShape(12.dp)
            ),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "About DecisionEngine",
            fontSize = typography.titleMedium.fontSize * 0.75f,
            color = Color.Black,
            modifier = Modifier
                .padding(top = 4.dp, bottom = 0.dp, start = 8.dp, end = 8.dp)
        )

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            thickness = 1.dp,
            color = Color.LightGray
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(
                2.dp,
                alignment = Alignment.CenterVertically
            )
        ) {
            SettingsSectionButton(
                title = "About the App",
                subtitle = "Learn more about DecisionEngine",
                logo = R.drawable.information_foreground,
                color = DecisionBlue,
                onClicked = {

                }
            )

            SettingsSectionButton(
                title = "Privacy Policy",
                subtitle = "See how we protect your data",
                logo = R.drawable.shield_foreground,
                color = DecisionGreen,
                onClicked = {

                }
            )

            SettingsSectionButton(
                title = "Terms of Use",
                subtitle = "Our terms and conditions",
                logo = R.drawable.filesymbol_foreground,
                color = FreeTrialPink,
                onClicked = {

                }
            )
        }
    }
}
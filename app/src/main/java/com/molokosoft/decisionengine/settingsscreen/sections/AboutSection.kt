package com.molokosoft.decisionengine.settingsscreen.sections

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.molokosoft.decisionengine.R
import com.molokosoft.decisionengine.settingsscreen.buttons.SettingsSectionButton
import com.molokosoft.decisionengine.settingsscreen.dialogs.AboutDialog
import com.molokosoft.decisionengine.settingsscreen.dialogs.PrivacyPolicyDialog
import com.molokosoft.decisionengine.settingsscreen.dialogs.TermsOfUseDialog

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
    var showPrivacyPolicyDialog by remember { mutableStateOf(false) }
    var showAboutDialog by remember { mutableStateOf(false) }
    var showTermsOfUseDialog by remember { mutableStateOf(false) }

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
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
            modifier = Modifier
                .padding(top = 4.dp, bottom = 0.dp, start = 16.dp, end = 8.dp)
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
                    showAboutDialog = true
                }
            )

            SettingsSectionButton(
                title = "Privacy Policy",
                subtitle = "See how we protect your data",
                logo = R.drawable.shield_foreground,
                color = DecisionGreen,
                onClicked = {
                    showPrivacyPolicyDialog = true
                }
            )

            SettingsSectionButton(
                title = "Terms of Use",
                subtitle = "Our terms and conditions",
                logo = R.drawable.filesymbol_foreground,
                color = FreeTrialPink,
                onClicked = {
                    showTermsOfUseDialog = true
                }
            )
        }
    }

    if (showPrivacyPolicyDialog) {
        PrivacyPolicyDialog(
            modifier = modifier,
            onDismissRequest = {
                showPrivacyPolicyDialog = false
            }
        )

        return
    }

    if (showAboutDialog) {
        AboutDialog(
            modifier = modifier,
            onDismissRequest = {
                showAboutDialog = false
            }
        )

        return
    }

    if (showTermsOfUseDialog) {
        TermsOfUseDialog(
            modifier = modifier,
            onDismissRequest = {
                showTermsOfUseDialog = false
            }
        )
    }
}
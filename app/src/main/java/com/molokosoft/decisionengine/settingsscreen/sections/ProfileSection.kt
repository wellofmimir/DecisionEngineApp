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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics

import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

import com.molokosoft.decisionengine.R
import com.molokosoft.decisionengine.settingsscreen.buttons.SettingsSectionButton
import com.molokosoft.decisionengine.settingsscreen.inputs.DecisionTextInput
import com.molokosoft.decisionengine.theme.DecisionBlueLight
import com.molokosoft.decisionengine.theme.LocalAppTypography



@Composable
fun ProfileSection(
    modifier: Modifier = Modifier,
    username: String,
    decisionsLeft: String,
    onNewUsernameEntered: (newUsername: String) -> Unit,
    onShowRestoreKeyClicked: () -> Unit
) {
    val typography =
        LocalAppTypography.current

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
            text = "Profile",
            fontSize = typography.titleMedium.fontSize * 0.75f,
            color = Color.Black,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
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
            DecisionTextInput( //kann hier verwendet werden zum Namen eintrage
                username = username,
                onNewUsernameEntered = {
                    onNewUsernameEntered(it)
                }
            )

            SettingsSectionButton(
                logo = R.drawable.analysis_foreground,
                color = DecisionBlueLight,
                title = "How many decisions can I analyze?",
                subtitle = "Decisions left: ${decisionsLeft.ifBlank { "-" }}",
                onClicked = {},
                showArrow = false
            )

            SettingsSectionButton(
                logo = R.drawable.padlock_foreground,
                color = Color.Magenta,
                title = "See my Restore Key",
                subtitle = "Tap here to see your Restore Key",
                onClicked = onShowRestoreKeyClicked
            )
        }
    }
}
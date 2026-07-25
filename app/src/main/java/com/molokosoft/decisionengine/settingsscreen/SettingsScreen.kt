package com.molokosoft.decisionengine.settingsscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue

import com.molokosoft.decisionengine.homescreen.viewmodel.HomeScreenViewModel
import com.molokosoft.decisionengine.theme.LocalAppTypography

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    homeScreenViewModel: HomeScreenViewModel
) {
    val typography = LocalAppTypography.current
    val verticalScroll = rememberScrollState()
    val username by homeScreenViewModel.username.collectAsState()

    Column(
        modifier = modifier
            .verticalScroll(verticalScroll)
            .background(
                color = Color.White
            )
            .fillMaxHeight()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(
            modifier = Modifier
                .height(16.dp)
        )

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Settings",
                    fontSize = typography.titleLarge.fontSize,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    textAlign = TextAlign.Start,
                    softWrap = true,
                    maxLines = 2
                )

                Text(
                    text = "Manage your profile and app preferences.",
                    fontSize = typography.titleMedium.fontSize * 0.75f,
                    fontWeight = FontWeight.Light,
                    color = Color.Black
                )
            }
        }

        Spacer(
            modifier = Modifier
                .weight(1f)
        )

        UsernameSection(
            username = username,
            onNewUsernameEntered = {
                homeScreenViewModel.setUsername(it)
            }
        )

        Spacer(
            modifier = Modifier
                .height(8.dp)
        )

        AboutSection()

        Spacer(
            modifier = Modifier
                .height(8.dp)
        )

        AppInformationSection()

        Spacer(
            modifier = Modifier
                .weight(1f)
        )

        Spacer(
            modifier = Modifier
                .height(8.dp)
        )
    }
}
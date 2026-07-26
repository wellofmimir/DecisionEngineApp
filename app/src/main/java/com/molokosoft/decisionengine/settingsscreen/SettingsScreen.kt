package com.molokosoft.decisionengine.settingsscreen

import android.content.ActivityNotFoundException
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import android.content.Context
import android.content.Intent
import com.molokosoft.decisionengine.settingsscreen.model.SettingsScreenViewModel
import com.molokosoft.decisionengine.theme.LocalAppTypography
import androidx.core.net.toUri

fun openPlayStoreRating(context: Context) {
    val packageName = context.packageName

    try {
        val intent = Intent(
            Intent.ACTION_VIEW,
            "market://details?.id=$packageName".toUri()
        )

        context.startActivity(intent)

    } catch (e: ActivityNotFoundException) {
        val intent = Intent(
            Intent.ACTION_VIEW,
            "https://play.google.com/store/apps/details?id=$packageName".toUri()
        )

        context.startActivity(intent)
    }
}

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    settingsScreenViewModel: SettingsScreenViewModel,
) {
    val context = LocalContext.current
    val typography = LocalAppTypography.current
    val verticalScroll = rememberScrollState()
    val username by settingsScreenViewModel.username.collectAsState()
    val feedbackLimitReached = settingsScreenViewModel.feedbackLimitReached()

    var showFeedbackBox by remember { mutableStateOf(false) }
    var showVersionInformationBox by remember { mutableStateOf(false) }

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
                settingsScreenViewModel.setUsername(it)
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

        AppInformationSection(
            onSendFeedbackRequested = {
                showFeedbackBox = true
            },
            onSeeVersionInformation = {
                showVersionInformationBox = true
            },
            onRateMyAppRequested = {
                openPlayStoreRating(context)
            }
        )

        Spacer(
            modifier = Modifier
                .weight(1f)
        )

        Spacer(
            modifier = Modifier
                .height(8.dp)
        )
    }

    if (showFeedbackBox) {
        EnterFeedbackBox(
            feedbackLimitReached = feedbackLimitReached,
            onDismissRequest = {
                showFeedbackBox = false
            },
            onSend = { feedback ->
                showFeedbackBox = false
                settingsScreenViewModel.saveFeedback(feedback)
            }
        )

        return
    }

    if (showVersionInformationBox) {
        VersionBox(
            onDismissRequest = {
                showVersionInformationBox = false
            },
            version = context.getAppVersionName()
        )

        return
    }
}
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
import androidx.compose.runtime.LaunchedEffect
import com.molokosoft.decisionengine.settingsscreen.viewmodel.SettingsScreenViewModel
import com.molokosoft.decisionengine.theme.LocalAppTypography
import androidx.core.net.toUri
import com.molokosoft.decisionengine.commonuielements.dialogs.RestoreKeyDialog
import com.molokosoft.decisionengine.settingsscreen.dialogs.EnterFeedbackDialog
import com.molokosoft.decisionengine.settingsscreen.dialogs.VersionDialog
import com.molokosoft.decisionengine.settingsscreen.sections.AboutSection
import com.molokosoft.decisionengine.settingsscreen.sections.AppInformationSection
import com.molokosoft.decisionengine.settingsscreen.sections.ProfileSection
import com.molokosoft.decisionengine.settingsscreen.sections.getAppVersionName

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
    val context =
        LocalContext.current

    val typography =
        LocalAppTypography.current

    val verticalScroll =
        rememberScrollState()

    val username by settingsScreenViewModel.username.collectAsState()
    val decisionsLeft by settingsScreenViewModel.decisionsLeft.collectAsState()
    val apiKey by remember { mutableStateOf(settingsScreenViewModel.apiKey()) }

    LaunchedEffect(Unit) {
        settingsScreenViewModel.getDecisionsLeft()
    }


    val feedbackLimitReached =
        settingsScreenViewModel.feedbackLimitReached()

    var showFeedbackDialog by remember { mutableStateOf(false) }
    var showVersionInformationDialog by remember { mutableStateOf(false) }
    var showRestoreKeyDialog by remember { mutableStateOf(false) }


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

        ProfileSection(
            modifier = Modifier,
            username = username,
            decisionsLeft = decisionsLeft,
            onNewUsernameEntered = {
                settingsScreenViewModel.setUsername(it)
            },
            onShowRestoreKeyClicked = {
                showRestoreKeyDialog = true
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
                showFeedbackDialog = true
            },
            onSeeVersionInformation = {
                showVersionInformationDialog = true
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

    if (showFeedbackDialog) {
        EnterFeedbackDialog(
            feedbackLimitReached = feedbackLimitReached,
            onDismissRequest = {
                showFeedbackDialog = false
            },
            onSend = { feedback ->
                showFeedbackDialog = false
                settingsScreenViewModel.saveFeedback(feedback)
            }
        )

        return
    }

    if (showVersionInformationDialog) {
        VersionDialog(
            onDismissRequest = {
                showVersionInformationDialog = false
            },
            version = context.getAppVersionName()
        )

        return
    }

    if (showRestoreKeyDialog) {
        RestoreKeyDialog(
            onDismissRequest = {
                showRestoreKeyDialog = false
            },
            restoreKey = apiKey
        )
    }
}
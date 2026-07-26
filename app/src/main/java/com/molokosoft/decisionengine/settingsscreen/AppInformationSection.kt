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
import com.molokosoft.decisionengine.theme.DecisionBlueLight
import com.molokosoft.decisionengine.theme.Purple40
import com.molokosoft.decisionengine.theme.Orange
import com.molokosoft.decisionengine.theme.LocalAppTypography
import androidx.compose.ui.platform.LocalContext
import android.content.Context

fun Context.getAppVersionName(): String {
    return try {
        val packageInfo = packageManager.getPackageInfo(packageName, 0)
        packageInfo.versionName ?: ""
    } catch (e: Exception) {
        ""
    }
}

@Composable
fun AppInformationSection(
    modifier: Modifier = Modifier,
    onSendFeedbackRequested: () -> Unit,
    onSeeVersionInformation: () -> Unit,
    onRateMyAppRequested: () -> Unit
) {
    val context = LocalContext.current
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
            text = "App Information",
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
                title = "Version",
                subtitle = context.getAppVersionName(),
                logo = R.drawable.layers_foreground,
                color = DecisionBlueLight,
                onClicked = {
                    onSeeVersionInformation()
                }
            )

            SettingsSectionButton(
                title = "Rate the App",
                subtitle = "If you enjoy using DecisionEngine, please rate us",
                logo = R.drawable.star_foreground,
                color = Orange,
                onClicked = {
                    onRateMyAppRequested()
                }
            )

            SettingsSectionButton(
                title = "Send Feedback",
                subtitle = "We'd love to hear your thoughts",
                logo = R.drawable.messagecircle_foreground,
                color = Purple40,
                onClicked = {
                    onSendFeedbackRequested()
                }
            )
        }
    }
}
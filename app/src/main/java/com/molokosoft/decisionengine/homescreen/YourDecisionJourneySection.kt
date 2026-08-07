package com.molokosoft.decisionengine.homescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.molokosoft.decisionengine.homescreen.viewmodel.model.Statistic
import com.molokosoft.decisionengine.theme.DecisionBlueLight
import com.molokosoft.decisionengine.theme.LocalAppTypography

@Composable
fun YourDecisionJourneySection(
    modifier: Modifier = Modifier,
    averageConfidence: Int,
    amountOfDecisions: Int,
    averageOptionsPerDecision: Int
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
            )
    ) {
        Text(
            text = "Your Decision Journey",
            fontSize = typography.titleMedium.fontSize * 0.75f,
            color = Color.Black,
            modifier = Modifier
                .padding(start = 8.dp, top = 4.dp)
        )

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            thickness = 1.dp,
            color = Color.LightGray
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(
                2.dp,
                alignment = Alignment.CenterHorizontally
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            JourneyCard(
                modifier = Modifier
                    .weight(1f),
                statistic = Statistic(
                    title = "Avg.\nConfidence",
                    subTitle = "",
                    value = if (averageConfidence == 0) "-" else "$averageConfidence%",
                    icon = R.drawable.upwards_trend_file_foreground
                )
            )

            JourneyCard(
                modifier = Modifier
                    .weight(1f),
                statistic = Statistic(
                    title = "Decisions\nMade",
                    subTitle = "",
                    value = if (amountOfDecisions.toString().trimIndent() == "0") "-" else amountOfDecisions.toString(),
                    icon = R.drawable.analysis_foreground
                )
            )

            JourneyCard(
                modifier = Modifier
                    .weight(1f),
                statistic = Statistic(
                    title = "Avg.\nOptions",
                    subTitle = "",
                    value = if (averageOptionsPerDecision.toString().trimIndent() == "0") "-" else averageOptionsPerDecision.toString(),
                    icon = R.drawable.star_blue_foreground
                )
            )
        }
    }
}
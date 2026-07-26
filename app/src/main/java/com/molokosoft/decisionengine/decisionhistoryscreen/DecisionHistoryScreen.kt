package com.molokosoft.decisionengine.decisionhistoryscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.molokosoft.decisionengine.R
import com.molokosoft.decisionengine.decisionhistoryscreen.viewmodel.DecisionHistoryViewModel
import com.molokosoft.decisionengine.theme.DecisionBlueLight
import com.molokosoft.decisionengine.theme.LocalAppTypography

@Composable
fun DecisionHistoryScreen(
    decisionHistoryViewModel: DecisionHistoryViewModel,
    modifier: Modifier = Modifier,
    onSettingsHistoryClicked: () -> Unit
) {
    val typography = LocalAppTypography.current
    val groups by decisionHistoryViewModel.historyItems.collectAsState()

    Column(
        modifier = modifier
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

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(80.dp)
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .weight(3f)
            ) {
                Text(
                    text = "Your Decision History",
                    fontSize = typography.titleLarge.fontSize,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    textAlign = TextAlign.Start,
                    softWrap = true,
                    maxLines = 2,
                    modifier = Modifier
                        .padding(bottom = 2.dp)
                )

                Text(
                    text = "Review your past decisions and revisit what mattered.",
                    fontSize = typography.titleMedium.fontSize * 0.75f,
                    fontWeight = FontWeight.Light,
                    color = Color.Black
                )
            }

            Spacer(
                modifier = Modifier
                    .width(16.dp)
            )

            Box(
                modifier = Modifier
                    .padding(top = 16.dp)
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
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(DecisionBlueLight)
                    .clickable {
                        onSettingsHistoryClicked()
                    },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.settings_foreground),
                    contentDescription = null,
                    modifier = Modifier
                        .size(26.dp)
                )
            }
        }

        Spacer(
            modifier = Modifier
                .weight(1f)
        )

        LazyColumn(
            modifier = modifier
                .background(
                    color = Color.White
                )
                .fillMaxHeight(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            if (groups.isEmpty())
                return@LazyColumn

            items(groups) { group ->
                DecisionsInAMonthSection(
                    modifier = Modifier
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
                    month = group.yearMonth,
                    decisions = group.decisions
                )
            }
        }
    }
}
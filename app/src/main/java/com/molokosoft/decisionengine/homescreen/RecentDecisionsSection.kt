package com.molokosoft.decisionengine.homescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import com.molokosoft.decisionengine.commonclasses.DecisionCategory
import com.molokosoft.decisionengine.commonclasses.getDecisionCategory
import com.molokosoft.decisionengine.commonuielements.DecisionButton
import com.molokosoft.decisionengine.database.relation.DecisionCompleteRelation
import com.molokosoft.decisionengine.decisionhistoryscreen.getColorToLogo
import com.molokosoft.decisionengine.decisionhistoryscreen.getLogo
import com.molokosoft.decisionengine.theme.DecisionBlue
import com.molokosoft.decisionengine.theme.DecisionBlueLight
import com.molokosoft.decisionengine.theme.LocalAppTypography

@Composable
fun RecentDecisionsSection(
    decisions: List<DecisionCompleteRelation>,
    modifier: Modifier = Modifier,
    onViewAllClicked: () -> Unit
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Recent Decisions",
                fontSize = typography.titleMedium.fontSize * 0.75f,
                color = Color.Black,
                modifier = Modifier
                    .padding(top = 4.dp)
            )

            Text(
                text = "View all",
                fontSize = typography.titleSmall.fontSize * 0.8f,
                color = DecisionBlue,
                modifier = Modifier
                    .padding(top = 4.dp, end = 12.dp)
                    .clickable() {
                        onViewAllClicked()
                    }
            )
        }

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
            decisions.forEachIndexed { index, decision ->
                if (index == 2)
                    return@Column

                val bestOption = decision.options.maxByOrNull {
                    it.option.confidence
                }

                DecisionButton(
                    decisionTitle = decision.decision.title,
                    amountOfOptionsText = decision.options.size.toString(),
                    score = bestOption?.option?.confidence.toString(),
                    color = getColorToLogo(decisionCategory = getDecisionCategory(decision.decision.category)),
                    decisionButtonLogo = getLogo(decisionCategory = getDecisionCategory(decision.decision.category)),
                    onClicked = { }
                )
            }
        }
    }
}
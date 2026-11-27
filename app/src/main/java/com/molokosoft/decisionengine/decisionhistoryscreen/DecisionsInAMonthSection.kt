package com.molokosoft.decisionengine.decisionhistoryscreen

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
import com.molokosoft.decisionengine.commonclasses.getDecisionCategory
import com.molokosoft.decisionengine.commonuielements.buttons.DecisionButton
import com.molokosoft.decisionengine.commonuielements.getColorToLogo
import com.molokosoft.decisionengine.commonuielements.getLogo
import java.time.YearMonth

import com.molokosoft.decisionengine.theme.DecisionBlueLight
import com.molokosoft.decisionengine.theme.LocalAppTypography
import com.molokosoft.decisionengine.database.relation.DecisionCompleteRelation

@Composable
fun DecisionsInAMonthSection(
    month: YearMonth,
    decisions: List<DecisionCompleteRelation>,
    modifier: Modifier = Modifier,
    onShowOldDecision: (oldDecision: DecisionCompleteRelation) -> Unit
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
            text = month.month.toString() + ", " + month.year.toString(),
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

        Column(
            verticalArrangement = Arrangement.spacedBy(
                2.dp,
                alignment = Alignment.CenterVertically
            )
        ) {
            decisions.forEach { it ->
                DecisionButton(
                    decisionTitle = it.decision.title,
                    amountOfOptionsText = it.options.size.toString(),
                    score = it.options.maxOfOrNull() { it ->
                        it.option.confidence
                    }.toString(),
                    color = getColorToLogo(decisionCategory = getDecisionCategory(it.decision.category)),
                    decisionButtonLogo = getLogo(decisionCategory = getDecisionCategory(it.decision.category)),
                    onClicked = {
                        onShowOldDecision(it)
                    }
                )
            }
        }
    }
}

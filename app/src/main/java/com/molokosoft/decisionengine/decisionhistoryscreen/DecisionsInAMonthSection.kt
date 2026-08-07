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
import com.molokosoft.decisionengine.R
import com.molokosoft.decisionengine.commonclasses.DecisionCategory
import com.molokosoft.decisionengine.commonclasses.getDecisionCategory
import com.molokosoft.decisionengine.commonuielements.DecisionButton
import java.time.YearMonth

import com.molokosoft.decisionengine.theme.DecisionBlueLight
import com.molokosoft.decisionengine.theme.LocalAppTypography
import com.molokosoft.decisionengine.database.relation.DecisionCompleteRelation

fun getLogo(decisionCategory: DecisionCategory): Int {
    return when (decisionCategory) {
        DecisionCategory.CAREER -> R.drawable.career_foreground
        DecisionCategory.FINANCE -> R.drawable.dollar_foreground
        DecisionCategory.RELATIONSHIPS -> R.drawable.relationship_foreground
        DecisionCategory.HOME -> R.drawable.home_foreground
        DecisionCategory.HEALTH -> R.drawable.health_foreground
        DecisionCategory.SHOPPING -> R.drawable.shopping_cart_foreground
        DecisionCategory.TRAVEL -> R.drawable.airplane_foreground
        DecisionCategory.EDUCATION -> R.drawable.education_foreground
        DecisionCategory.LIFESTYLE -> R.drawable.lifestyle_foreground
        DecisionCategory.OTHER -> R.drawable.analysis_foreground
    }
}

fun getColorToLogo(decisionCategory: DecisionCategory): Color {
    return when (decisionCategory) {
        DecisionCategory.CAREER -> Color(0xFF1976D2)        // Blau
        DecisionCategory.FINANCE -> Color(0xFF2E7D32)       // Grün
        DecisionCategory.RELATIONSHIPS -> Color(0xFFE91E63) // Pink
        DecisionCategory.HOME -> Color(0xFF8D6E63)          // Braun
        DecisionCategory.HEALTH -> Color(0xFFD32F2F)        // Rot
        DecisionCategory.SHOPPING -> Color(0xFFFF9800)      // Orange
        DecisionCategory.TRAVEL -> Color(0xFF00ACC1)        // Türkis
        DecisionCategory.EDUCATION -> Color(0xFF5E35B1)     // Violett
        DecisionCategory.LIFESTYLE -> Color(0xFFFFC107)     // Amber
        DecisionCategory.OTHER -> Color(0xFF757575)         // Grau
    }
}

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
                    score = "100",
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

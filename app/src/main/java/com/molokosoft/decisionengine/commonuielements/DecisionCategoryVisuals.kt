package com.molokosoft.decisionengine.commonuielements

import androidx.compose.ui.graphics.Color
import com.molokosoft.decisionengine.R
import com.molokosoft.decisionengine.commonclasses.DecisionCategory

fun getLogo(decisionCategory: DecisionCategory): Int =
    when (decisionCategory) {
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

fun getColorToLogo(decisionCategory: DecisionCategory): Color =
    when (decisionCategory) {
        DecisionCategory.CAREER -> Color(0xFF1976D2)
        DecisionCategory.FINANCE -> Color(0xFF2E7D32)
        DecisionCategory.RELATIONSHIPS -> Color(0xFFE91E63)
        DecisionCategory.HOME -> Color(0xFF8D6E63)
        DecisionCategory.HEALTH -> Color(0xFFD32F2F)
        DecisionCategory.SHOPPING -> Color(0xFFFF9800)
        DecisionCategory.TRAVEL -> Color(0xFF00ACC1)
        DecisionCategory.EDUCATION -> Color(0xFF5E35B1)
        DecisionCategory.LIFESTYLE -> Color(0xFFFFC107)
        DecisionCategory.OTHER -> Color(0xFF757575)
    }

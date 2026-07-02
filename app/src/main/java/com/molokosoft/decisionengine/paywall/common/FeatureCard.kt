package com.molokosoft.decisionengine.paywall.common

import androidx.annotation.DrawableRes

data class FeatureCard(
    @DrawableRes val imageResource: Int,
    val text: String
)
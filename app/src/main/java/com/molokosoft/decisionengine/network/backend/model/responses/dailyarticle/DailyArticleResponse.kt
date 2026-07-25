package com.molokosoft.decisionengine.network.backend.model.responses.dailyarticle

import com.molokosoft.decisionengine.network.backend.model.dto.dailyarticle.DailyArticle
import kotlinx.serialization.Serializable

@Serializable
data class DailyArticleResponse(
    val dailyArticle: DailyArticle
)

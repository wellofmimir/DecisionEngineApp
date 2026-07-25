package com.molokosoft.decisionengine.repositories

import com.molokosoft.decisionengine.homescreen.viewmodel.model.Article
import com.molokosoft.decisionengine.network.backend.DecisionEngineClient
import com.molokosoft.decisionengine.network.backend.model.dto.dailyarticle.DailyArticle
import com.molokosoft.decisionengine.network.backend.model.responses.dailyarticle.DailyArticleResponse

fun DailyArticleResponse.toArticle(): Article {
    return Article(
        title = this.dailyArticle.title,
        topic = this.dailyArticle.topic,
        readingTimeMinutes = this.dailyArticle.readingTimeMinutes,
        summary = this.dailyArticle.summary,
        content = this.dailyArticle.content,
        takeAwayPoints = this.dailyArticle.takeAwayPoints
    )
}

class ArticlesRepository(
    private val decisionEngineClient: DecisionEngineClient
) {
    suspend fun dailyArticle(): Article? {
        val dailyArticleResponse = decisionEngineClient.dailyArticle()
        return dailyArticleResponse?.toArticle()
    }
}
package com.molokosoft.decisionengine.repositories

import android.content.Context
import androidx.room.withTransaction
import com.molokosoft.decisionengine.database.DatabaseProvider
import com.molokosoft.decisionengine.database.entities.DailyArticleEntity
import com.molokosoft.decisionengine.homescreen.viewmodel.model.Article
import com.molokosoft.decisionengine.network.backend.DecisionEngineClient
import com.molokosoft.decisionengine.network.backend.model.responses.dailyarticle.DailyArticleResponse
import com.molokosoft.decisionengine.preferences.SecurePreferences

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
    context: Context,
    private val securePreferences: SecurePreferences,
    private val decisionEngineClient: DecisionEngineClient
) {
    private val database =
        DatabaseProvider.getDatabase(context)

    private val dao =
        database.dailyArticleDao()

    fun setDailyArticleObtained() {
        securePreferences.setDailyArticleObtained()
    }

    fun dailyArticleObtained(): Boolean {
        return securePreferences.dailyArticleObtained()
    }

    suspend fun saveDailyArticle(article: Article) {
        database.withTransaction {
            dao.insertArticle(
                DailyArticleEntity(
                    title = article.title,
                    summary = article.summary,
                    content = article.content
                )
            )
        }
    }

    suspend fun dailyArticle(): Article? {
        if (dailyArticleObtained()) {
            var latestArticle = DailyArticleEntity()

            database.withTransaction {
                latestArticle = dao.getLatestArticle()
            }

            return Article(
                title = latestArticle.title,
                topic = "",
                readingTimeMinutes = 1,
                summary= latestArticle.summary,
                content= latestArticle.content,
                takeAwayPoints = emptyList()
            )
        }

        val dailyArticleResponse = decisionEngineClient.dailyArticle()

        if (dailyArticleResponse == null)
            return null

        saveDailyArticle(dailyArticleResponse.toArticle())
        setDailyArticleObtained()

        return dailyArticleResponse.toArticle()
    }
}
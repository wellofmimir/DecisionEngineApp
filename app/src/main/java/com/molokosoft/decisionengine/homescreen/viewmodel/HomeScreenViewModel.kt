package com.molokosoft.decisionengine.homescreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.molokosoft.decisionengine.decisionhistoryscreen.model.DecisionMonthGroup
import kotlinx.coroutines.flow.MutableStateFlow
import com.molokosoft.decisionengine.homescreen.viewmodel.model.Article
import com.molokosoft.decisionengine.repositories.ArticlesRepository
import com.molokosoft.decisionengine.repositories.DecisionRepository
import com.molokosoft.decisionengine.repositories.UserDataRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.YearMonth
import java.time.ZoneId
import kotlin.collections.component1
import kotlin.collections.component2

class HomeScreenViewModel(
    private val articlesRepository: ArticlesRepository,
    private val decisionRepository: DecisionRepository,
    private val userDataRepository: UserDataRepository
) : ViewModel() {

    private val _article =
        MutableStateFlow(Article())

    private val _username =
        MutableStateFlow(username())

    val username =
        _username.asStateFlow()
    val article =
        _article.asStateFlow()
    fun setUsername(username: String) {
        userDataRepository.setUsername(username)
        _username.value = username
    }

    private fun username(): String {
        return userDataRepository.username()
    }

    val historyItems: StateFlow<List<DecisionMonthGroup>> =
        decisionRepository.decisionHistory
            .map { decisions ->
                decisions.groupBy {
                    Instant.ofEpochMilli(it.decision.createdAt)
                        .atZone(ZoneId.systemDefault())
                        .let { zoned ->
                            YearMonth.from(zoned)
                        }
                }
                    .map { (month, decisions) ->
                        DecisionMonthGroup(
                            yearMonth = month,
                            decisions = decisions
                        )
                    }
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )


    fun getDailyArticle() {
        viewModelScope.launch {
            val dailyArticle = articlesRepository.dailyArticle()

            if (dailyArticle == null)
                return@launch

            _article.update {
                it.copy(
                    title = dailyArticle.title,
                    topic = dailyArticle.topic,
                    readingTimeMinutes = dailyArticle.readingTimeMinutes,
                    summary = dailyArticle.summary,
                    content = dailyArticle.content,
                    takeAwayPoints = dailyArticle.takeAwayPoints
                )
            }

            articlesRepository.setDailyArticleObtained()
        }
    }
}
package com.molokosoft.decisionengine.decisionhistoryscreen.viewmodel

import com.molokosoft.decisionengine.repositories.DecisionRepository
import com.molokosoft.decisionengine.decisionhistoryscreen.model.DecisionMonthGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.time.Instant
import java.time.YearMonth
import java.time.ZoneId

class DecisionHistoryViewModel(
    decisionRepository: DecisionRepository
) : ViewModel() {

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
}
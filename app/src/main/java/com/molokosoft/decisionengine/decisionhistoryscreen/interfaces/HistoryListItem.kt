package com.molokosoft.decisionengine.decisionhistoryscreen.interfaces

import com.molokosoft.decisionengine.database.relation.DecisionCompleteRelation
import java.time.YearMonth

sealed interface HistoryListItem {

    data class MonthHeader(
        val yearMonth: YearMonth
    ): HistoryListItem

    data class DecisionItem(
        val decision: DecisionCompleteRelation
    ): HistoryListItem
}
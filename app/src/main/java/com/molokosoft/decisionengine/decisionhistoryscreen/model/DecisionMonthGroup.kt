package com.molokosoft.decisionengine.decisionhistoryscreen.model

import java.time.YearMonth
import com.molokosoft.decisionengine.database.relation.DecisionCompleteRelation

data class DecisionMonthGroup(
    val yearMonth: YearMonth,
    val decisions: List<DecisionCompleteRelation>
)
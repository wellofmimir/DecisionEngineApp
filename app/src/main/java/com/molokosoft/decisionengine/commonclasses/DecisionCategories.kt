package com.molokosoft.decisionengine.commonclasses

enum class DecisionCategory {
    CAREER,
    FINANCE,
    RELATIONSHIPS,
    HOME,
    HEALTH,
    SHOPPING,
    TRAVEL,
    EDUCATION,
    LIFESTYLE,
    OTHER
}

fun getDecisionCategory(category: String): DecisionCategory {
    return DecisionCategory.entries.firstOrNull() {
        it.name.equals(category, ignoreCase = true)
    } ?: DecisionCategory.OTHER
}
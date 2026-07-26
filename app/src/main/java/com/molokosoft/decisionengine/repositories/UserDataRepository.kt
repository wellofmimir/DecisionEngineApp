package com.molokosoft.decisionengine.repositories

import com.molokosoft.decisionengine.network.backend.DecisionEngineClient
import com.molokosoft.decisionengine.preferences.SecurePreferences

class UserDataRepository(
    private val decisionEngineClient: DecisionEngineClient,
    private val securePreferences: SecurePreferences
) {
    suspend fun sendEmail(eMail: String): Boolean {
        return decisionEngineClient.sendEmail(eMail)
    }

    suspend fun sendFeedback(feedback: String): Boolean {
        return decisionEngineClient.sendFeedback(feedback)
    }

    fun feedbackLimitReached(): Boolean {
        return securePreferences.feedbackLimitReached()
    }

    fun setFeedbackLimitReached() {
        securePreferences.setFeedbackLimitReached()
    }

    fun resetFeedbackLimitReached() {
        securePreferences.resetFeedbackLimitReached()
    }

    fun setUsername(username: String) {
        securePreferences.setUsername(username)
    }

    fun username(): String {
        return securePreferences.username()
    }
}
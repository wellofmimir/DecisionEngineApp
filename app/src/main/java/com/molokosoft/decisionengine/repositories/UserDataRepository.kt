package com.molokosoft.decisionengine.repositories

import com.molokosoft.decisionengine.network.backend.DecisionEngineClient
import com.molokosoft.decisionengine.preferences.SecurePreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserDataRepository(
    private val decisionEngineClient: DecisionEngineClient,
    private val securePreferences: SecurePreferences
) {
    private val _username =
        MutableStateFlow(username())

    val username =
        _username.asStateFlow()

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
        _username.value = username
    }

    fun username(): String {
        return securePreferences.username()
    }
}
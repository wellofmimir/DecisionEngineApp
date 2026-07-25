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

    fun firstDecisionDone(): Boolean {
        return securePreferences.firstDecisionDone()
    }

    fun setFirstDecisionDone() {
        securePreferences.setFirstDecisionDone()
    }

    fun setUsername(username: String) {
        securePreferences.setUsername(username)
    }

    fun username(): String {
        return securePreferences.username()
    }
}
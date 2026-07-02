package com.molokosoft.decisionengine.repositories

import com.molokosoft.decisionengine.network.backend.DecisionEngineClient

class UserDataRepository(private val decisionEngineClient: DecisionEngineClient) {

    suspend fun sendEmail(eMail: String): Boolean {
        return decisionEngineClient.sendEmail(eMail)
    }
}
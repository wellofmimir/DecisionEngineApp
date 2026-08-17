package com.molokosoft.decisionengine.repositories

import com.molokosoft.decisionengine.network.backend.DecisionEngineClient

class BackendRepository(
    private val decisionEngineClient: DecisionEngineClient
) {
    suspend fun checkAvailability(): Boolean {
        return decisionEngineClient.getHealth() != null
    }
}
package com.molokosoft.decisionengine.network.backend.model.responses.billing

import com.molokosoft.decisionengine.network.backend.model.dto.billing.AccessStatus
import kotlinx.serialization.Serializable

@Serializable
data class AccessStatusResponse(
    val accessStatus: AccessStatus
)

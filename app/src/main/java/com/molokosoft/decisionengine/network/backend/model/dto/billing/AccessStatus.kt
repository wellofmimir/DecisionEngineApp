package com.molokosoft.decisionengine.network.backend.model.dto.billing

import kotlinx.serialization.Serializable

@Serializable
data class AccessStatus(
    val remainingUsages: Int
)

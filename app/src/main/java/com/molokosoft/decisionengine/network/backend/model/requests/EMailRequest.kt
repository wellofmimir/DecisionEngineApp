package com.molokosoft.decisionengine.network.backend.model.requests

import kotlinx.serialization.Serializable

@Serializable
data class EMailRequest(
    val eMail: String
)

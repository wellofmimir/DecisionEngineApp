package com.molokosoft.decisionengine.network.backend.model.dto.security.requests

import kotlinx.serialization.Serializable

@Serializable
data class PromptReconnaissanceRequest(
    val prompt: String
)
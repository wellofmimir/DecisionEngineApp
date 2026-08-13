package com.molokosoft.decisionengine.network.backend.model.dto.security.dto

import kotlinx.serialization.Serializable

@Serializable
data class PromptReconnaissanceResult(
    val isPrompt: Boolean,
    val reason: String
)

package com.molokosoft.decisionengine.network.backend.model.dto.security.responses

import kotlinx.serialization.Serializable
import com.molokosoft.decisionengine.network.backend.model.dto.security.dto.PromptReconnaissanceResult

@Serializable
data class PromptReconnaissanceResponse(
    val result: PromptReconnaissanceResult
)

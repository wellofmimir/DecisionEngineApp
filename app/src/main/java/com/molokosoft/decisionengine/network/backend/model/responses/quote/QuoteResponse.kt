package com.molokosoft.decisionengine.network.backend.model.responses.quote

import com.molokosoft.decisionengine.network.backend.model.dto.quote.Quote
import kotlinx.serialization.Serializable

@Serializable
data class QuoteResponse(
    val quote: Quote
)

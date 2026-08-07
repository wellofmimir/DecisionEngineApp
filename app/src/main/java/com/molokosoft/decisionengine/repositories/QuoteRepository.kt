package com.molokosoft.decisionengine.repositories

import android.content.Context
import com.molokosoft.decisionengine.network.backend.DecisionEngineClient
import com.molokosoft.decisionengine.preferences.SecurePreferences
import com.molokosoft.decisionengine.network.backend.model.dto.quote.Quote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class QuoteRepository(
    context: Context,
    private val securePreferences: SecurePreferences,
    private val decisionEngineClient: DecisionEngineClient
) {
    suspend fun getMotivationalQuote(): Result<Quote> =
        withContext(Dispatchers.IO) {
            runCatching {
                decisionEngineClient.getQuote()?.quote
                    ?: throw IllegalStateException("Quote response was null. Failed to obtain a motivational quote.")
            }
        }

    fun saveMotivationalQuote(quote: String, person: String) {
        securePreferences.saveMotivationalQuote(quote, person)
    }

    fun motivationalQuote(): Pair<String, String> {
        return securePreferences.motivationalQuote()
    }
}
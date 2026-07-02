package com.molokosoft.decisionengine.network.backend

import com.molokosoft.decisionengine.network.backend.model.requests.DecisionAnalysisRequest
import com.molokosoft.decisionengine.network.backend.model.responses.DecisionAnalysisResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import kotlinx.serialization.encodeToString
import android.util.Log
import com.molokosoft.decisionengine.network.backend.model.dto.CriterionSuggestion
import com.molokosoft.decisionengine.network.backend.model.responses.ApiResponse
import com.molokosoft.decisionengine.network.backend.model.responses.CriteriaSuggestionResponse
import org.json.JSONObject

class DecisionEngineClient(private val client: OkHttpClient) {

    suspend fun analyze(decisionAnalysisRequest: DecisionAnalysisRequest): DecisionAnalysisResponse? =
        withContext(Dispatchers.IO) {
            val mediaType = "application/json; charset=utf-8".toMediaType()
            val json = Json.encodeToString(decisionAnalysisRequest)
            val requestBody = json.toRequestBody(mediaType)

            val request = Request.Builder()
                .post(requestBody)
                .url("http://192.168.188.21:45003/api/v1/decision/analyze")
                .build()

            try {
                client.newCall(request).execute().use { response ->
                    val responseBody = response.body?.string() ?: ""
                    val apiResponse = Json.decodeFromString<ApiResponse<DecisionAnalysisResponse>>(responseBody)
                    return@withContext apiResponse.data
                }
            } catch (e: Exception) {
                Log.e("DecisionEngine", "Network error", e)
                Log.d("DecisionEngine", Json.encodeToString(decisionAnalysisRequest))
                e.printStackTrace()
                null
            }
        }

    suspend fun sendEmail(eMail: String): Boolean =
        withContext(Dispatchers.IO) {
            val mediaType = "application/json; charset=utf-8".toMediaType()
            val json = JSONObject()
                .put("eMail", eMail)

            val requestBody = json.toString().toRequestBody(mediaType)

            val request = Request.Builder()
                .post(requestBody)
                .url("http://192.168.188.21:45003/api/v1/email")
                .build()

            try {
                client.newCall(request).execute().use { response ->
                    val responseBody = response.body?.string() ?: ""
                    val apiResponse = Json.decodeFromString<ApiResponse<Unit>>(responseBody)
                    return@withContext apiResponse.success
                }
            } catch (e: Exception) {
                Log.e("DecisionEngine", "Network error", e)
                e.printStackTrace()
                false
            }
        }

    suspend fun getCriteriaSuggestions(decisionTitle: String): CriteriaSuggestionResponse? =
        withContext(Dispatchers.IO) {
            val mediaType = "application/json; charset=utf-8".toMediaType()
            val json = JSONObject()
                .put("decisionTitle", decisionTitle)

            val requestBody = json.toString().toRequestBody(mediaType)

            val request = Request.Builder()
                .post(requestBody)
                .url("http://192.168.188.21:45003/api/v1/criteria/suggest")
                .build()

            try {
                client.newCall(request).execute().use { response ->
                    val responseBody = response.body?.string() ?: ""
                    val apiResponse = Json.decodeFromString<ApiResponse<CriteriaSuggestionResponse>>(responseBody)
                    return@withContext apiResponse.data
                }
            } catch (e: Exception) {
                Log.e("DecisionEngine", "Network error", e)
                e.printStackTrace()
                null
            }
        }
}
package com.molokosoft.decisionengine.network.backend

import com.molokosoft.decisionengine.BuildConfig
import com.molokosoft.decisionengine.network.backend.model.requests.decision.DecisionAnalysisRequest
import com.molokosoft.decisionengine.network.backend.model.responses.decision.DecisionAnalysisResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import kotlinx.serialization.encodeToString
import android.util.Log
import com.molokosoft.decisionengine.AppEvent
import com.molokosoft.decisionengine.network.backend.model.dto.billing.AccessStatus
import com.molokosoft.decisionengine.network.backend.model.dto.security.requests.PromptReconnaissanceRequest
import com.molokosoft.decisionengine.network.backend.model.dto.security.responses.PromptReconnaissanceResponse
import com.molokosoft.decisionengine.network.backend.model.requests.billing.VerifyPurchaseRequest
import com.molokosoft.decisionengine.network.backend.model.requests.decision.SafetyClassificationRequest
import com.molokosoft.decisionengine.network.backend.model.responses.ApiResponse
import com.molokosoft.decisionengine.network.backend.model.responses.billing.AccessStatusResponse
import com.molokosoft.decisionengine.network.backend.model.responses.billing.VerifyPurchaseResponse
import com.molokosoft.decisionengine.network.backend.model.responses.dailyarticle.DailyArticleResponse
import com.molokosoft.decisionengine.network.backend.model.responses.decision.CriteriaSuggestionResponse
import com.molokosoft.decisionengine.network.backend.model.responses.decision.SafetyClassificationResponse
import com.molokosoft.decisionengine.network.backend.model.responses.health.HealthResponse
import com.molokosoft.decisionengine.network.backend.model.responses.quote.QuoteResponse
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import okio.IOException
import org.json.JSONObject

val baseUrl = if (BuildConfig.DEBUG)
    "https://greeen-app.com/dec"
else
    "https://greeen-app.com/dec"

class DecisionEngineClient(
    private val client: OkHttpClient
) {
    private var apiKey: String = ""

    fun setApiKey(key: String) {
        apiKey = key
    }

    private var installationId: String = ""

    fun setInstallationId(id: String) {
        installationId = id
    }
    
    suspend fun getHealth(): HealthResponse? =
        withContext(Dispatchers.IO) {
            val request = Request.Builder()
                .get()
                .url("$baseUrl/health")
                .build()

            try {
                client.newCall(request).execute().use { response ->
                    val responseBody = response.body?.string() ?: ""
                    val apiResponse = Json.decodeFromString<ApiResponse<HealthResponse>>(responseBody)
                    return@withContext apiResponse.data
                }
            } catch (e: Exception) {
                Log.e("DecisionEngine", "Network error", e)
                e.printStackTrace()
                null
            }
        }

    suspend fun getRemainingUsages(): AccessStatusResponse? =
        withContext(Dispatchers.IO) {
            val request = Request.Builder()
                .addHeader("X-Installation-ID", installationId)
                .addHeader("Authorization", "Bearer $apiKey")
                .get()
                .url("$baseUrl/api/v1/billing/status")
                .build()

            try {
                client.newCall(request).execute().use { response ->
                    val responseBody = response.body?.string() ?: ""
                    val apiResponse = Json.decodeFromString<ApiResponse<AccessStatusResponse>>(responseBody)
                    return@withContext apiResponse.data
                }
            } catch (e: Exception) {
                Log.e("DecisionEngine", "Network error", e)
                Log.d("DecisionEngine", Json.encodeToString(AccessStatusResponse))
                e.printStackTrace()
                null
            }
        }
    suspend fun verifyPurchase(verifyPurchaseRequest: VerifyPurchaseRequest): VerifyPurchaseResponse? =
        withContext(Dispatchers.IO) {
            val mediaType = "application/json; charset=utf-8".toMediaType()
            val json = Json.encodeToString(verifyPurchaseRequest)
            val requestBody = json.toRequestBody(mediaType)

            val request = Request.Builder()
                .addHeader("X-Installation-ID", installationId)
                .post(requestBody)
                .url("$baseUrl/api/v1/billing/verify")
                .build()

            try {
                client.newCall(request).execute().use { response ->

                    if (!response.isSuccessful)
                        throw IOException("HTTP ${response.code}: ${response.message}")

                    val responseBody = response.body?.string() ?: ""
                    val apiResponse = Json.decodeFromString<ApiResponse<VerifyPurchaseResponse>>(responseBody)
                    return@withContext apiResponse.data
                }
            } catch (e: Exception) {
                Log.e("DecisionEngine", "Network error", e)
                Log.d("DecisionEngine", Json.encodeToString(VerifyPurchaseRequest))
                e.printStackTrace()
                null
            }
        }

    suspend fun dailyArticle(): DailyArticleResponse? =
        withContext(Dispatchers.IO) {
            val request = Request.Builder()
                .addHeader("X-Installation-ID", installationId)
                .addHeader("Authorization", "Bearer $apiKey")
                .get()
                .url("$baseUrl/api/v1/articles/daily")
                .build()

            try {
                client.newCall(request).execute().use { response ->
                    val responseBody = response.body?.string() ?: ""
                    val apiResponse = Json.decodeFromString<ApiResponse<DailyArticleResponse>>(responseBody)
                    return@withContext apiResponse.data
                }
            } catch (e: Exception) {
                Log.e("DecisionEngine", "Network error", e)
                e.printStackTrace()
                null
            }
        }

    suspend fun analyze(decisionAnalysisRequest: DecisionAnalysisRequest): DecisionAnalysisResponse? =
        withContext(Dispatchers.IO) {
            val mediaType = "application/json; charset=utf-8".toMediaType()
            val json = Json.encodeToString(decisionAnalysisRequest)
            val requestBody = json.toRequestBody(mediaType)

            val request = Request.Builder()
                .addHeader("X-Installation-ID", installationId)
                .addHeader("Authorization", "Bearer $apiKey")
                .post(requestBody)
                .url("$baseUrl/api/v1/decision/analyze")
                .build()

            Log.d("DecisionEngine", "========== ANALYZE START ==========")
            Log.d("DecisionEngine", "URL: ${request.url}")
            Log.d("DecisionEngine", "Method: ${request.method}")
            Log.d("DecisionEngine", "API Key present: ${apiKey.isNotBlank()}")
            Log.d("DecisionEngine", "Request body: $json")

            try {
                Log.d("DecisionEngine", "Sending request...")

                client.newCall(request).execute().use { response ->

                    Log.d(
                        "DecisionEngine",
                        "Response received: ${response.code} ${response.message}"
                    )

                    if (!response.isSuccessful)
                        throw IOException("HTTP ${response.code}: ${response.message}")

                    val responseBody = response.body?.string() ?: ""

                    Log.d(
                        "DecisionEngine",
                        "Response body: $responseBody"
                    )

                    val apiResponse = Json.decodeFromString<ApiResponse<DecisionAnalysisResponse>>(responseBody)

                    Log.d("DecisionEngine", "Response parsed successfully")
                    Log.d("DecisionEngine", "========== ANALYZE SUCCESS ==========")

                    return@withContext apiResponse.data
                }
            } catch (e: Exception) {
                Log.e("DecisionEngine", "Network error", e)
                Log.d("DecisionEngine", Json.encodeToString(decisionAnalysisRequest))

                Log.e(
                    "DecisionEngine",
                    "Network request failed",
                    e
                )

                Log.e(
                    "DecisionEngine",
                    "Exception type: ${e::class.java.name}"
                )

                Log.e(
                    "DecisionEngine",
                    "Exception message: ${e.message}"
                )

                Log.e(
                    "DecisionEngine",
                    "Request body was: $json"
                )

                Log.d("DecisionEngine", "========== ANALYZE FAILED ==========")

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
                .addHeader("X-Installation-ID", installationId)
                .post(requestBody)
                .url("$baseUrl/api/v1/email")
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

    suspend fun sendFeedback(feedback: String): Boolean =
        withContext(Dispatchers.IO) {
            val mediaType = "application/json; charset=utf-8".toMediaType()
            val json = JSONObject()
                .put("feedback", feedback)

            val requestBody = json.toString().toRequestBody(mediaType)

            val request = Request.Builder()
                .addHeader("X-Installation-ID", installationId)
                .addHeader("Authorization", "Bearer $apiKey")
                .post(requestBody)
                .url("$baseUrl/api/v1/feedback/send")
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
                .addHeader("X-Installation-ID", installationId)
                .post(requestBody)
                .url("$baseUrl/api/v1/criteria/suggest")
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

    suspend fun safetyClassification(safetyClassificationRequest: SafetyClassificationRequest): SafetyClassificationResponse? =
        withContext(Dispatchers.IO) {
            val mediaType = "application/json; charset=utf-8".toMediaType()
            val json = JSONObject()
                .put("title", safetyClassificationRequest.title)

            val requestBody = json.toString().toRequestBody(mediaType)

            val request = Request.Builder()
                .addHeader("X-Installation-ID", installationId)
                .post(requestBody)
                .url("$baseUrl/api/v1/decision/safetyClassification")
                .build()

            try {
                client.newCall(request).execute().use { response ->
                    val responseBody = response.body?.string() ?: ""
                    val apiResponse = Json.decodeFromString<ApiResponse<SafetyClassificationResponse>>(responseBody)
                    return@withContext apiResponse.data
                }
            } catch (e: Exception) {
                Log.e("DecisionEngine", "Network error", e)
                e.printStackTrace()
                null
            }
        }

    suspend fun getQuote(): QuoteResponse? =
        withContext(Dispatchers.IO) {
            val request = Request.Builder()
                .addHeader("X-Installation-ID", installationId)
                .addHeader("Authorization", "Bearer $apiKey")
                .get()
                .url("$baseUrl/api/v1/quotes/daily")
                .build()

            try {
                client.newCall(request).execute().use { response ->
                    val responseBody = response.body?.string() ?: ""
                    val apiResponse = Json.decodeFromString<ApiResponse<QuoteResponse>>(responseBody)
                    return@withContext apiResponse.data
                }
            } catch (e: Exception) {
                Log.e("DecisionEngine", "Network error", e)
                Log.d("DecisionEngine", Json.encodeToString(DailyArticleResponse))
                e.printStackTrace()
                null
            }
        }

    suspend fun promptReconnaissance(promptReconnaissanceRequest: PromptReconnaissanceRequest): PromptReconnaissanceResponse? =
        withContext(Dispatchers.IO) {
            val mediaType =
                "application/json; charset=utf-8".toMediaType()

            val json = JSONObject()
                .put("prompt", promptReconnaissanceRequest.prompt)

            val requestBody =
                json.toString().toRequestBody(mediaType)

            val request =
                Request.Builder()
                    .addHeader("X-Installation-ID", installationId)
                    .post(requestBody)
                    .url("$baseUrl/api/v1/security/promptReconnaissance")
                    .build()

            try {
                client.newCall(request).execute().use { response ->
                    val responseBody =
                        response.body?.string() ?: ""

                    val apiResponse =
                        Json.decodeFromString<ApiResponse<PromptReconnaissanceResponse>>(responseBody)

                    return@withContext apiResponse.data
                }
            } catch (e: Exception) {
                Log.e("DecisionEngine", "Network error", e)
                e.printStackTrace()
                null
            }
        }
}
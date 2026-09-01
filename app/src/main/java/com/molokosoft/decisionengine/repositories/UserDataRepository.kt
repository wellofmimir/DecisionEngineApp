package com.molokosoft.decisionengine.repositories

import androidx.core.content.edit
import com.molokosoft.decisionengine.network.backend.DecisionEngineClient
import com.molokosoft.decisionengine.network.backend.model.requests.billing.VerifyPurchaseRequest
import com.molokosoft.decisionengine.preferences.SecurePreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserDataRepository(
    private val decisionEngineClient: DecisionEngineClient,
    private val securePreferences: SecurePreferences
) {
    private val _username =
        MutableStateFlow(securePreferences.username())

    val username =
        _username.asStateFlow()

    private val _ratingInfoDialogSeen =
        MutableStateFlow(securePreferences.ratingInfoDialogSeen())

    val ratingInfoDialogSeen =
        _ratingInfoDialogSeen.asStateFlow()

    fun setRatingInfoDialogSeen() {
        securePreferences.setRatingInfoDialogSeen()

        _ratingInfoDialogSeen.value =
            securePreferences.ratingInfoDialogSeen()
    }

    private val _importanceInfoDialogSeen =
        MutableStateFlow(securePreferences.importanceInfoDialogSeen())

    val importanceInfoDialogSeen =
        _importanceInfoDialogSeen.asStateFlow()

    fun setImportanceInfoDialogSeen() {
        securePreferences.setImportanceInfoDialogSeen()

        _importanceInfoDialogSeen.value =
            securePreferences.importanceInfoDialogSeen()
    }

    private val _optionInfoDialogSeen =
        MutableStateFlow(securePreferences.optionInfoDialogSeen())

    val optionInfoDialogSeen =
        _optionInfoDialogSeen.asStateFlow()

    fun setOptionInfoDialogSeen() {
        securePreferences.setOptionInfoDialogSeen()

        _optionInfoDialogSeen.value =
            securePreferences.optionInfoDialogSeen()
    }

    private var _decisionInfoDialogSeen =
        MutableStateFlow(securePreferences.decisionInfoDialogSeen())

    val decisionInfoDialogSeen =
        _decisionInfoDialogSeen.asStateFlow()

    fun setDecisionInfoDialogSeen() {
        securePreferences.setDecisionInfoDialogSeen()

        _decisionInfoDialogSeen.value =
            securePreferences.decisionInfoDialogSeen()
    }

    private val _restoreKeyInformationSeen =
        MutableStateFlow(securePreferences.restoreKeyInformationSeen())

    val restoreKeyInformationSeen =
        _restoreKeyInformationSeen.asStateFlow()

    fun setRestoreKeyInformationSeen() {
        securePreferences.setRestoreKeyInformationSeen()

        _restoreKeyInformationSeen.value =
            securePreferences.restoreKeyInformationSeen()
    }

    suspend fun verifyPurchase(purchaseToken: String, productId: String, apiKey: String?): Boolean {
        return decisionEngineClient.verifyPurchase(
            VerifyPurchaseRequest(
                purchaseToken = purchaseToken,
                productId = productId,
                apiKey = apiKey
            )
        )?.let {
            securePreferences.setApiKey(it.apiKey)
            decisionEngineClient.setApiKey(securePreferences.apiKey())
            true
        } ?: false
    }

    suspend fun decisionsLeft(): String {
        return decisionEngineClient.getRemainingUsages()?.accessStatus?.remainingUsages?.toString() ?: ""
    }

    fun apiKey(): String {
        return securePreferences.apiKey()
    }

    suspend fun sendEmail(eMail: String): Boolean {
        return decisionEngineClient.sendEmail(eMail)
    }

    suspend fun sendFeedback(feedback: String): Boolean {
        return decisionEngineClient.sendFeedback(feedback)
    }

    fun feedbackLimitReached(): Boolean {
        return securePreferences.feedbackLimitReached()
    }

    fun setFeedbackLimitReached() {
        securePreferences.setFeedbackLimitReached()
    }

    fun resetFeedbackLimitReached() {
        securePreferences.resetFeedbackLimitReached()
    }

    fun setUsername(username: String) {
        securePreferences.setUsername(username)
        _username.value = username
    }
}
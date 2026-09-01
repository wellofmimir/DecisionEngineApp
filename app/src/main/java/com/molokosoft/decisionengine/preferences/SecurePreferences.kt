package com.molokosoft.decisionengine.preferences

import android.content.Context
import kotlin.getValue
import androidx.security.crypto.MasterKey
import androidx.security.crypto.EncryptedSharedPreferences
import android.content.ContentValues
import androidx.core.content.edit

class SecurePreferences(
    private val context: Context
) {
    val masterKey =
        MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val securePreferences =
        EncryptedSharedPreferences.create(
            context,
            "SecurePreferences",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

    fun ratingInfoDialogSeen(): Boolean {
        return securePreferences.getBoolean("ratingInfoDialogSeen", false)
    }

    fun setRatingInfoDialogSeen() {
        securePreferences.edit {
            putBoolean("ratingInfoDialogSeen", true)
        }
    }

    fun importanceInfoDialogSeen(): Boolean {
        return securePreferences.getBoolean("importanceInfoDialogSeen", false)
    }

    fun setImportanceInfoDialogSeen() {
        securePreferences.edit {
            putBoolean("importanceInfoDialogSeen", true)
        }
    }

    fun optionInfoDialogSeen(): Boolean {
        return securePreferences.getBoolean("optionInfoDialogSeen", false)
    }

    fun setOptionInfoDialogSeen() {
        securePreferences.edit {
            putBoolean("optionInfoDialogSeen", true)
        }
    }

    fun decisionInfoDialogSeen(): Boolean {
        return securePreferences.getBoolean("decisionInfoDialogSeen", false)
    }

    fun setDecisionInfoDialogSeen() {
        securePreferences.edit {
            putBoolean("decisionInfoDialogSeen", true)
        }
    }

    fun restoreKeyInformationSeen(): Boolean {
        return securePreferences.getBoolean("restoreKeyInformationSeen", false)
    }

    fun setRestoreKeyInformationSeen() {
        securePreferences.edit {
            putBoolean("restoreKeyInformationSeen", true)
        }
    }

    fun installationId(): String {
        return securePreferences.getString("installationId", "")
            ?: ""
    }

    fun setInstallationId(installationId: String) {
        securePreferences.edit {
            putString("installationId", installationId)
        }
    }

    fun setApiKey(apiKey: String) {
        securePreferences.edit {
            putString("apiKey", apiKey)
        }
    }

    fun apiKey(): String {
        return securePreferences.getString("apiKey", "")
            ?: ""
    }

    fun setFeedbackLimitReached() {
        securePreferences.edit {
            putBoolean("feedbackLimitReached", true)
        }
    }

    fun resetFeedbackLimitReached() {
        securePreferences.edit {
            putBoolean("feedbackLimitReached", false)
        }
    }

    fun feedbackLimitReached(): Boolean {
        return securePreferences.getBoolean("feedbackLimitReached", false)
    }

    fun setUsername(username: String) {
        securePreferences.edit {
            putString("username", username)
        }
    }

    fun username(): String {
        return securePreferences.getString("username", "")
            ?: ""
    }

    fun setDailyArticleObtained() {
        securePreferences.edit {
            putBoolean("dailyArticle", true)
        }
    }

    fun dailyArticleObtained(): Boolean {
        return securePreferences.getBoolean("dailyArticle", false)
    }

    fun saveMotivationalQuote(quote: String, person: String) {
        securePreferences.edit {
            putString("quote", quote)
            putString("person", person)
        }
    }

    fun motivationalQuote(): Pair<String, String> {
        val quote = securePreferences.getString("quote", "Every mistake seems incredibly stupid when others make it.")
            ?: "Every mistake seems incredibly stupid when others make it."

        val person = securePreferences.getString("person", "Georg Christoph Lichtenberg")
            ?: "Georg Christoph Lichtenberg"

        return quote to person
    }
}
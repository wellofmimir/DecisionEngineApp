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
    private val sharedPreferences =
        context.getSharedPreferences("SecurePreferences", Context.MODE_PRIVATE)

    private val masterKey by lazy {
        MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
    }

    private val securePreferences by lazy {
        EncryptedSharedPreferences.create(
            context,
            "SecurePreferences",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun setFirstDecisionDone() {
        securePreferences.edit {
            putBoolean("firstDecisionDone", true)
        }
    }

    fun firstDecisionDone(): Boolean {
        return securePreferences.getBoolean("firstDecisionDone", false)
    }

    fun setAPIKey(apiKey: String) {
        securePreferences.edit {
            putString("APIKey", apiKey)
        }
    }

    fun setUsername(username: String) {
        securePreferences.edit {
            putString("username", username)
        }
    }

    fun username(): String {
        return securePreferences.getString("username", "") ?: ""
    }

    fun apiKey(): String {
        return securePreferences.getString("APIKey", "") ?: ""
    }
}
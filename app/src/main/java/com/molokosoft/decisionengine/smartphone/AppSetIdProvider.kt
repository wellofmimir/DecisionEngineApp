package com.molokosoft.decisionengine.smartphone

import android.content.Context
import kotlinx.coroutines.suspendCancellableCoroutine
import com.google.android.gms.appset.AppSet
import kotlin.coroutines.resumeWithException

class AppSetIdProvider(
    private val context: Context
) {
    suspend fun getAppSetId(): String =
        suspendCancellableCoroutine { continuation ->
            val client = AppSet.getClient(context)

            client.appSetIdInfo
                .addOnSuccessListener { info ->
                    continuation.resume(info.id, onCancellation = null)
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
        }
}
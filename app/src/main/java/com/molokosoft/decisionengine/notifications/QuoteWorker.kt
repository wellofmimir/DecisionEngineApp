package com.molokosoft.decisionengine.notifications

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import android.content.Context
import androidx.work.*
import com.molokosoft.decisionengine.network.SharedHttpClient
import com.molokosoft.decisionengine.network.backend.DecisionEngineClient
import com.molokosoft.decisionengine.preferences.SecurePreferences
import com.molokosoft.decisionengine.repositories.QuoteRepository
import java.util.concurrent.TimeUnit


class QuoteWorker (
    context: Context,
    workerParams: WorkerParameters
): CoroutineWorker (context, workerParams) {

    companion object {
        fun enqueue(context: Context) {
            val request = OneTimeWorkRequestBuilder<QuoteWorker>()
                .setConstraints (
                    Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .build()
                )
                .setBackoffCriteria (
                    BackoffPolicy.LINEAR,
                    5,
                    TimeUnit.MINUTES
                )
                .build()

            WorkManager.getInstance(context)
                .enqueueUniqueWork (
                    "motivationalQuote",
                    ExistingWorkPolicy.KEEP,
                    request
                )
        }
    }

    override suspend fun doWork(): Result =
        withContext(Dispatchers.IO) {
            try {
                val securePreferences = SecurePreferences(applicationContext)
                val decisionEngineClient = DecisionEngineClient(SharedHttpClient.sharedClient)
                val quoteRepository = QuoteRepository(applicationContext, securePreferences, decisionEngineClient)

                //TODO("Einbauen, dass das hier nicht stattfindet, wenn der User kein Abo hat.")

                val quote = quoteRepository.getMotivationalQuote()
                    .getOrElse {
                        return@withContext if (runAttemptCount >= 10)
                            Result.failure()
                        else
                            Result.retry()
                    }

                val notifier = Notifier(applicationContext)
                notifier.sendMotivationalQuote(quote.quote, "-" + quote.person)

                quoteRepository.saveMotivationalQuote(quote.quote, quote.person)
                Result.success()

            } catch (e: Exception) {
                if (runAttemptCount >= 10)
                    Result.failure()
                else
                    Result.retry()
            }
    }
}
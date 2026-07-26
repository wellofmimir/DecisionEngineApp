package com.molokosoft.decisionengine.notifications

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import android.content.Context
import androidx.work.*
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
                    "motivational_quote",
                    ExistingWorkPolicy.KEEP,
                    request
                )
        }
    }

    override suspend fun doWork(): Result =
        withContext(Dispatchers.IO) {
            try {
                val notifier = Notifier(applicationContext)
                notifier.sendMotivationalQuote("Beer or not to beer?", "Patryk")

                Result.success()
            } catch (e: Exception) {
                if (runAttemptCount >= 10)
                    Result.failure()
                else
                    Result.retry()
            }
    }
}
package com.molokosoft.decisionengine.notifications

import android.Manifest
import android.app.PendingIntent

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager

import android.os.Build

import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat

import com.molokosoft.decisionengine.MainActivity
import com.molokosoft.decisionengine.R

class Notifier(
    private val context: Context
) {
    fun sendMotivationalQuote(quote: String, quotee: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED)
                return
        }

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity (
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, "motivational_quote")
            .setSmallIcon(R.drawable.decisionenginelogonew)
            .setColor(ContextCompat.getColor(context, R.color.white))
            .setContentTitle("A quote for you:")
            .setContentText(quote + "\n" + quotee)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        val notificationId = System.currentTimeMillis().toInt()
        NotificationManagerCompat.from(context).notify(notificationId, notification.build())
    }
}
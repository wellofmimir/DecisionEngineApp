package com.molokosoft.decisionengine.notifications

import android.content.Context
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import java.util.Calendar

object QuoteAlarmScheduler {
    private const val REQUEST_CODE = 2000

    fun schedule(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, QuoteAlarmReceiver::class.java)

        val pendingIntent = PendingIntent.getBroadcast (
            context,
            REQUEST_CODE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 55)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)

            if (before(Calendar.getInstance()))
                add(Calendar.DAY_OF_MONTH, 1)
        }

        alarmManager.setAndAllowWhileIdle (
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
    }
}
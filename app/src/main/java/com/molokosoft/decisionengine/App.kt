package com.molokosoft.decisionengine

import android.app.Application
import com.molokosoft.decisionengine.notifications.QuoteAlarmScheduler

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        QuoteAlarmScheduler.schedule(this)
    }
}
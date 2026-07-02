package com.molokosoft.decisionengine.network

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

object SharedHttpClient {
    val sharedClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .callTimeout(30, TimeUnit.SECONDS)
        .build()
}
package com.khaki.smartrss

import android.app.Application

class SmartRssApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Koinの初期化
        initKoin()
    }
}
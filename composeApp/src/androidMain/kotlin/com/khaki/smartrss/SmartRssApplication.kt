package com.khaki.smartrss

import android.app.Application
import org.koin.android.ext.koin.androidContext

class SmartRssApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Koinの初期化（AndroidのContextをDIへ注入）
        initKoin(
            appDeclaration = {
                androidContext(this@SmartRssApplication)
            }
        )
    }
}

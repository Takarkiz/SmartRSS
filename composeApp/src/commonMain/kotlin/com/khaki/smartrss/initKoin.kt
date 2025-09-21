package com.khaki.smartrss

import com.khaki.api.apiClientModule
import com.khaki.api.platformClientEngineModule
import com.khaki.smartrss.di.appModule
import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(
            platformClientEngineModule,
            apiClientModule,
            appModule,
        )
    }
}

package com.khaki.smartrss

import com.khaki.api.apiClientModule
import com.khaki.api.platformClientEngineModule
import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(
            platformClientEngineModule,
            apiClientModule,
        )
    }
}
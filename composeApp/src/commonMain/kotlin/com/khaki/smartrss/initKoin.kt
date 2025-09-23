package com.khaki.smartrss

import com.khaki.api.apiClientModule
import com.khaki.api.platformClientEngineModule
import com.khaki.room.commonDatabaseModule
import com.khaki.room.platformDatabaseModule
import com.khaki.smartrss.di.appModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(
    appDeclaration: KoinAppDeclaration = {},
) {
    startKoin {
        appDeclaration()

        modules(
            platformClientEngineModule,
            apiClientModule,
            appModule,
            commonDatabaseModule(),
            platformDatabaseModule(),
        )
    }
}

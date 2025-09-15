package com.khaki.api

import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformClientEngineModule: Module = module {
    single {
        OkHttp.create()
    }
}
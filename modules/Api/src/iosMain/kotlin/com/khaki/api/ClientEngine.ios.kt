package com.khaki.api

import io.ktor.client.engine.darwin.Darwin
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformClientEngineModule: Module = module {
    single {
        Darwin.create()
    }
}

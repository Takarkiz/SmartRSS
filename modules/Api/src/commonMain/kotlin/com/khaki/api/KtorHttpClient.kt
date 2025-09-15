package com.khaki.api

import com.khaki.api.service.RSSApiService
import com.khaki.api.service.impl.RssApiServiceImpl
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val apiClientModule = module {
    single {
        HttpClient(get<HttpClientEngine>()) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                })
            }
            install(Logging) {
                // TODO: リリース時にはロギングの対象を見直すこと
                // 参考: https://github.com/Takarkiz/SmartRSS/pull/4#discussion_r2348330125
                level = LogLevel.ALL
            }
        }
    }

    singleOf(::RssApiServiceImpl) bind RSSApiService::class
}

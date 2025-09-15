package com.khaki.api

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val apiClientModule = module {
    single {
        HttpClient(get<HttpClientEngine>()) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true // APIレスポンスに知らないキーあってもスルー！
                    isLenient = true
                })
            }
            install(Logging) { // ログ見る設定
                level = LogLevel.ALL
            }
        }
    }
}
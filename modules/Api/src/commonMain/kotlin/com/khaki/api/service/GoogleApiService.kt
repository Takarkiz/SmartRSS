package com.khaki.api.service

interface GoogleApiService {

    suspend fun getFaviconUrl(domain: String): String?
}

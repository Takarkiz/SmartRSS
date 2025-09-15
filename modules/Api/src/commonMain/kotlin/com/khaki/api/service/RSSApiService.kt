package com.khaki.api.service

import com.khaki.api.dto.QiitaRssFeedDto

interface RSSApiService {

    suspend fun fetchQiitaRssFeed(requestUrl: String): QiitaRssFeedDto

    suspend fun fetchRssFeed(requestUrl: String): String?
}

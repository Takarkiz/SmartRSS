package com.khaki.api.service

import com.khaki.api.dto.QiitaRssFeedDto
import com.khaki.api.dto.ZennRssFeedDto

interface RSSApiService {

    suspend fun fetchQiitaRssFeed(requestUrl: String): QiitaRssFeedDto

    suspend fun fetchZennRssFeed(requestUrl: String): ZennRssFeedDto

    suspend fun fetchRssFeed(requestUrl: String): String?
}

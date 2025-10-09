package com.khaki.api.service

import com.khaki.api.dto.HatenaRssFeedDto
import com.khaki.api.dto.QiitaRssFeedDto
import com.khaki.api.dto.RssFeedDto
import com.khaki.api.dto.ZennRssFeedDto

interface RSSApiService {

    suspend fun fetchQiitaRssFeed(requestUrl: String): QiitaRssFeedDto

    suspend fun fetchZennRssFeed(requestUrl: String): ZennRssFeedDto

    suspend fun fetchHatenaRssFeed(requestUrl: String): HatenaRssFeedDto

    suspend fun fetchRssFeed(requestUrl: String): RssFeedDto
}

package com.khaki.api.service.impl

import com.khaki.api.dto.HatenaRssFeedDto
import com.khaki.api.dto.QiitaRssFeedDto
import com.khaki.api.dto.RssFeedDto
import com.khaki.api.dto.ZennRssFeedDto
import com.khaki.api.service.RSSApiService
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import nl.adaptivity.xmlutil.serialization.XML

class RssApiServiceImpl(
    private val httpClient: HttpClient
) : RSSApiService {
    override suspend fun fetchQiitaRssFeed(requestUrl: String): QiitaRssFeedDto {
        val response = httpClient.get(requestUrl).bodyAsText()
        return XML.decodeFromString(QiitaRssFeedDto.serializer(), response)
    }

    override suspend fun fetchZennRssFeed(requestUrl: String): ZennRssFeedDto {
        val response = httpClient.get(requestUrl).bodyAsText()
        return XML.decodeFromString(ZennRssFeedDto.serializer(), response)
    }

    override suspend fun fetchHatenaRssFeed(requestUrl: String): HatenaRssFeedDto {
        val response = httpClient.get(requestUrl).bodyAsText()
        return XML.decodeFromString(HatenaRssFeedDto.serializer(), response)
    }

    override suspend fun fetchRssFeed(requestUrl: String): RssFeedDto {
        val response = httpClient.get(requestUrl).bodyAsText()
        return XML.decodeFromString(RssFeedDto.serializer(), response)
    }
}

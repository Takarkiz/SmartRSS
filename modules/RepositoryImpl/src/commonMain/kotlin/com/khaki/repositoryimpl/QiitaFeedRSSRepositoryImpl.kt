package com.khaki.repositoryimpl

import com.khaki.api.dto.QiitaRssFeedDto
import com.khaki.api.service.RSSApiService
import com.khaki.modules.core.model.feed.FeedItem
import com.khaki.modules.core.model.feed.RSSFeed
import com.khaki.repository.QiitaFeedRSSRepository
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime

class QiitaFeedRSSRepositoryImpl(
    private val apiService: RSSApiService
) : QiitaFeedRSSRepository {

    companion object {
        const val BASE_URL = "https://qiita.com"
    }

    override suspend fun popularFeeds(): RSSFeed {
        val response = apiService.fetchQiitaRssFeed("$BASE_URL/popular-items/feed.atom")
        return mapToDomain(response)
    }

    override suspend fun feedsByTag(tag: String): RSSFeed {
        val response = apiService.fetchQiitaRssFeed("$BASE_URL/tags/$tag/feed.atom")
        return mapToDomain(response)
    }

    override suspend fun feedsByUserId(userId: String): RSSFeed {
        val response = apiService.fetchQiitaRssFeed("$BASE_URL/$userId/feed.atom")
        return mapToDomain(response)
    }

    @OptIn(ExperimentalTime::class)
    private fun mapToDomain(dto: QiitaRssFeedDto): RSSFeed {
        return RSSFeed(
            title = dto.title,
            link = dto.links.first { it.rel == "self" }.href,
            description = "",
            items = dto.entries.map { entry ->
                FeedItem(
                    id = entry.articleId,
                    title = entry.title,
                    link = entry.link.href,
                    description = entry.content.value,
                    pubDate = Instant.parse(entry.pubDate)
                        .toLocalDateTime(TimeZone.currentSystemDefault()),
                    rssType = FeedItem.RSSType.Qiita(
                        authorName = entry.author.name,
                        updatedDate = Instant.parse(entry.updatedDate).toLocalDateTime(
                            TimeZone.currentSystemDefault()
                        )
                    )
                )
            }
        )
    }
}

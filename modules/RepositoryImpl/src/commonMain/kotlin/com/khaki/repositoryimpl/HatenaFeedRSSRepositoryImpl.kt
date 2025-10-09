package com.khaki.repositoryimpl

import com.khaki.api.dto.HatenaRssFeedDto
import com.khaki.api.service.RSSApiService
import com.khaki.modules.core.model.feed.FeedItem
import com.khaki.modules.core.model.feed.RSSFeed
import com.khaki.repository.HatenaFeedRSSRepository
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class HatenaFeedRSSRepositoryImpl(
    private val apiService: RSSApiService
) : HatenaFeedRSSRepository {

    override suspend fun feedsByUserId(userId: String): RSSFeed {
        val url = "https://$userId/feed"
        val dto = apiService.fetchHatenaRssFeed(url)
        return mapToDomain(dto)
    }

    private fun mapToDomain(dto: HatenaRssFeedDto): RSSFeed {
        return RSSFeed(
            title = dto.title,
            link = dto.links.firstOrNull { it.href != null }?.href ?: "",
            description = dto.subtitle ?: "",
            items = dto.entries.map { entry ->
                val mainLink =
                    entry.links.firstOrNull { it.href != null && (it.rel == null || it.rel == "alternate") }?.href
                        ?: entry.links.firstOrNull { it.href != null }?.href
                        ?: ""
                val enclosureLink =
                    entry.links.firstOrNull { it.rel == "enclosure" && it.href != null }?.href
                val id = entry.id
                val pub = entry.published ?: entry.updated
                FeedItem(
                    id = id,
                    title = entry.title,
                    link = mainLink,
                    description = entry.summary?.value ?: entry.content?.value ?: "",
                    pubDate = parseHatenaDate(pub),
                    rssType = FeedItem.RSSType.Hatena(
                        authorName = entry.author?.name ?: "",
                        thumbnailUrl = enclosureLink
                    )
                )
            }
        )
    }

    @OptIn(ExperimentalTime::class)
    private fun parseHatenaDate(value: String?): LocalDateTime {
        if (value.isNullOrBlank()) return nowLocal()
        // Hatena provides ISO-8601 with offset, e.g. 2025-10-04T17:02:27+09:00
        return runCatching {
            Instant.parse(value).toLocalDateTime(TimeZone.currentSystemDefault())
        }.getOrElse { nowLocal() }
    }

    @OptIn(ExperimentalTime::class)
    private fun nowLocal(): LocalDateTime =
        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
}

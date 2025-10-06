package com.khaki.repositoryimpl

import com.khaki.api.dto.ZennRssFeedDto
import com.khaki.api.service.RSSApiService
import com.khaki.modules.core.model.feed.FeedItem
import com.khaki.modules.core.model.feed.RSSFeed
import com.khaki.repository.ZennFeedRSSRepository
import kotlinx.datetime.*
import kotlin.time.ExperimentalTime

class ZennFeedRSSRepositoryImpl(
    private val apiService: RSSApiService
) : ZennFeedRSSRepository {

    companion object {
        const val BASE_URL = "https://zenn.dev"
        private val MONTHS = mapOf(
            "Jan" to 1, "Feb" to 2, "Mar" to 3, "Apr" to 4, "May" to 5, "Jun" to 6,
            "Jul" to 7, "Aug" to 8, "Sep" to 9, "Oct" to 10, "Nov" to 11, "Dec" to 12
        )
        private val RFC1123_REGEX = Regex(
            pattern = "^[A-Za-z]{3},\\s(\\d{1,2})\\s([A-Za-z]{3})\\s(\\d{4})\\s(\\d{2}):(\\d{2}):(\\d{2})\\s(GMT|UTC|[+-]\\d{4})$"
        )
    }

    override suspend fun popularFeeds(): RSSFeed {
        val dto = apiService.fetchZennRssFeed("$BASE_URL/feed")
        return mapToDomain(dto)
    }

    override suspend fun feedsByTag(tag: String): RSSFeed {
        val dto = apiService.fetchZennRssFeed("$BASE_URL/topics/$tag/feed")
        return mapToDomain(dto)
    }

    override suspend fun feedsByUserId(userId: String): RSSFeed {
        val dto = apiService.fetchZennRssFeed("$BASE_URL/$userId/feed")
        return mapToDomain(dto)
    }

    private fun mapToDomain(dto: ZennRssFeedDto): RSSFeed {
        val channel = dto.channel
        return RSSFeed(
            title = channel.title,
            link = channel.link,
            description = channel.description,
            items = channel.items.map { item ->
                val id = item.guid?.value ?: item.link
                FeedItem(
                    id = id,
                    title = item.title,
                    link = item.link,
                    description = item.description,
                    pubDate = parsePubDate(item.pubDate),
                    rssType = FeedItem.RSSType.Zenn(
                        authorName = item.creator ?: "",
                        thumbnailUrl = item.enclosure?.url
                    )
                )
            }
        )
    }

    @OptIn(ExperimentalTime::class)
    private fun parsePubDate(value: String?): LocalDateTime {
        if (value.isNullOrBlank()) return nowLocal()
        // Try ISO-8601 first (just in case)
        runCatching { return Instant.parse(value).toLocalDateTime(TimeZone.currentSystemDefault()) }
        // Try RFC1123 (e.g. "Sun, 05 Oct 2025 13:54:24 GMT")
        RFC1123_REGEX.matchEntire(value)?.let { m ->
            val day = m.groupValues[1].toInt()
            val monStr = m.groupValues[2]
            val year = m.groupValues[3].toInt()
            val hour = m.groupValues[4].toInt()
            val min = m.groupValues[5].toInt()
            val sec = m.groupValues[6].toInt()
            val month = MONTHS[monStr] ?: 1
            val ldt = LocalDateTime(year, month, day, hour, min, sec)
            // Treat as UTC regardless of the trailing zone token (Zenn uses GMT)
            return ldt.toInstant(TimeZone.UTC).toLocalDateTime(TimeZone.currentSystemDefault())
        }
        return nowLocal()
    }

    private fun nowLocal(): LocalDateTime = LocalDateTime(1970, 1, 1, 0, 0, 0)
}

package com.khaki.repositoryimpl

import com.khaki.api.dto.RssFeedDto
import com.khaki.api.service.RSSApiService
import com.khaki.modules.core.model.feed.FeedItem
import com.khaki.modules.core.model.feed.RSSFeed
import com.khaki.repository.OtherFeedRssRepository
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class OtherFeedRssRepositoryImpl(
    private val apiService: RSSApiService
) : OtherFeedRssRepository {

    override suspend fun feedsByUrl(url: String): RSSFeed {
        val dto = apiService.fetchRssFeed(url)
        return mapToDomain(dto)
    }

    private fun mapToDomain(dto: RssFeedDto): RSSFeed {
        return RSSFeed(
            title = dto.channel.title,
            link = dto.channel.link,
            description = dto.channel.description,
            items = dto.channel.items.map {
                FeedItem(
                    id = it.guid,
                    title = it.title,
                    link = it.link,
                    description = it.description,
                    pubDate = parseRfc1123(it.pubDate),
                    rssType = FeedItem.RSSType.Other(
                        thumbnailUrl = it.enclosure?.url
                    )
                )
            }
        )
    }

    // A simple parser for RFC-1123 date format like "Wed, 08 Oct 2025 13:31:25 GMT"
    @OptIn(ExperimentalTime::class)
    private fun parseRfc1123(dateString: String): LocalDateTime {
        return try {
            // Clean up and split the date string
            val parts = dateString.split(" ").filter { it.isNotBlank() }
            if (parts.size < 5) return nowLocal()

            val day = parts[1].toInt()
            val monthNumber = monthMap[parts[2]] ?: return nowLocal()
            val year = parts[3].toInt()

            val timeParts = parts[4].split(":")
            val hour = timeParts[0].toInt()
            val minute = timeParts[1].toInt()
            val second = timeParts[2].toInt()

            // The date is in GMT, we can create a LocalDateTime directly
            // Note: This does not handle timezones other than GMT.
            LocalDateTime(year, monthNumber, day, hour, minute, second)
        } catch (e: Exception) {
            // Fallback for parsing errors
            nowLocal()
        }
    }

    private val monthMap = mapOf(
        "Jan" to 1, "Feb" to 2, "Mar" to 3, "Apr" to 4, "May" to 5, "Jun" to 6,
        "Jul" to 7, "Aug" to 8, "Sep" to 9, "Oct" to 10, "Nov" to 11, "Dec" to 12
    )

    @OptIn(ExperimentalTime::class)
    private fun nowLocal(): LocalDateTime =
        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
}





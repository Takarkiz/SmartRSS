package com.khaki.repositoryimpl

import com.khaki.modules.core.model.feed.FeedItem
import com.khaki.modules.core.model.feed.FeedItem.RSSType
import com.khaki.repository.RssFeedRepository
import com.khaki.room.dao.RssFeedDao
import com.khaki.room.entity.RssFeedEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDateTime

class RssFeedRepositoryImpl(
    private val database: RssFeedDao
): RssFeedRepository {
    override fun getFeeds(): Flow<List<FeedItem>> {
        return database.getFeeds()
            .map { feeds ->
                feeds.map { entity -> entity.toModel() }
            }
    }

    override suspend fun addFeed(feed: FeedItem) {
        database.insertFeed(feed.toEntity())
    }

    override suspend fun addFeeds(feeds: List<FeedItem>) {
        database.insertFeeds(feeds.map { it.toEntity() })
    }
}

private fun RssFeedEntity.toModel(): FeedItem {
    val pub = runCatching { LocalDateTime.parse(pubDate) }.getOrElse { nowFallback() }
    return FeedItem(
        id = id,
        title = title,
        link = feedUrl,
        pubDate = pub,
        description = description,
        rssType = RSSType.Other(thumbnailUrl = null),
        tag = tagIds,
        isRead = isRead,
        isFavorite = isFavorite,
        isBookmarked = isBookmarked,
        recommendScore = recommendScore,
    )
}

private fun FeedItem.toEntity(): RssFeedEntity = RssFeedEntity(
    id = id,
    title = title,
    feedUrl = link,
    pubDate = pubDate.toString(),
    description = description,
    categoryId = "", // not available in domain model
    tagIds = tag,
    isShowed = false,
    isRead = isRead,
    isFavorite = isFavorite,
    isBookmarked = isBookmarked,
    recommendScore = recommendScore,
)

private fun nowFallback(): LocalDateTime =
    // Keep a stable string format; parse may fail if DB has legacy values
    runCatching { LocalDateTime.parse("1970-01-01T00:00:00") }.getOrThrow()

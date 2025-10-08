package com.khaki.repositoryimpl

import com.khaki.modules.core.model.feed.FeedItem
import com.khaki.modules.core.model.feed.FeedItem.RSSType
import com.khaki.repository.RssFeedRepository
import com.khaki.room.dao.RssFeedDao
import com.khaki.room.entity.RSSCategoryGroupDetail
import com.khaki.room.entity.RssFeedEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDateTime

class RssFeedRepositoryImpl(
    private val database: RssFeedDao
) : RssFeedRepository {
    override fun getFeeds(): Flow<List<FeedItem>> {
        return database.getFeeds()
            .map { feeds ->
                feeds.map { entity -> entity.toModel() }
            }
    }

    override suspend fun getFeed(id: String): FeedItem? {
        return database.getFeedsById(id)?.toModel()
    }

    override suspend fun addFeed(feed: FeedItem) {
        database.insertFeed(feed.toEntity())
    }

    override suspend fun addFeeds(feeds: List<FeedItem>) {
        database.insertFeeds(feeds.map { it.toEntity() })
    }

    override suspend fun updateBookmark(id: String, isBookmark: Boolean) {
        database.updateBookmarkedState(id, isBookmark)
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
        rssType = when (type) {
            is RSSCategoryGroupDetail.Qiita -> RSSType.Qiita(
                authorName = (type as RSSCategoryGroupDetail.Qiita).authorName,
            )

            is RSSCategoryGroupDetail.Zenn -> RSSType.Zenn(
                authorName = (type as RSSCategoryGroupDetail.Zenn).authorName,
                thumbnailUrl = (type as RSSCategoryGroupDetail.Zenn).thumbnailUrl,
            )

            is RSSCategoryGroupDetail.Hatena -> RSSType.Hatena(
                authorName = (type as RSSCategoryGroupDetail.Hatena).authorName,
                thumbnailUrl = (type as RSSCategoryGroupDetail.Hatena).thumbnailUrl,
            )

            is RSSCategoryGroupDetail.Other -> RSSType.Other(
                thumbnailUrl = (type as RSSCategoryGroupDetail.Other).thumbnailUrl,
            )
        },
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
    categoryId = "",
    type = when (rssType) {
        is RSSType.Qiita -> RSSCategoryGroupDetail.Qiita(
            authorName = (rssType as RSSType.Qiita).authorName,
        )

        is RSSType.Zenn -> RSSCategoryGroupDetail.Zenn(
            authorName = (rssType as RSSType.Zenn).authorName,
            thumbnailUrl = (rssType as RSSType.Zenn).thumbnailUrl,
        )

        is RSSType.Hatena -> RSSCategoryGroupDetail.Hatena(
            authorName = (rssType as RSSType.Hatena).authorName,
            thumbnailUrl = (rssType as RSSType.Hatena).thumbnailUrl,
        )

        is RSSType.Other -> RSSCategoryGroupDetail.Other(
            thumbnailUrl = (rssType as RSSType.Other).thumbnailUrl,
        )
    },
    tagIds = tag,
    isShowed = false,
    isRead = isRead,
    isFavorite = isFavorite,
    isBookmarked = isBookmarked,
    recommendScore = recommendScore,
)

private fun nowFallback(): LocalDateTime =
    runCatching { LocalDateTime.parse("1970-01-01T00:00:00") }.getOrThrow()

package com.khaki.repositoryimpl

import com.khaki.modules.core.model.feed.FeedItem
import com.khaki.modules.core.model.feed.FeedItem.RSSType
import com.khaki.modules.core.model.feed.UserRating
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

    override suspend fun addFeeds(feeds: List<FeedItem>) {
        database.insertFeeds(feeds.map { it.toEntity() })
    }

    override suspend fun updateBookmark(id: String, isBookmarked: Boolean) {
        database.updateBookmarkedState(id, isBookmarked)
    }

    override suspend fun updateUserRating(id: String, userRating: UserRating) {
        database.updateUserRating(
            id = id,
            userRating = when (userRating) {
                UserRating.Bad -> com.khaki.room.entity.UserRating.Bad
                UserRating.Good -> com.khaki.room.entity.UserRating.Good
                UserRating.None -> com.khaki.room.entity.UserRating.None
            }
        )
    }

    override suspend fun doAsRead(id: String) {
        database.updateReadState(id)
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
        rssType = when (val type = type) {
            is RSSCategoryGroupDetail.Qiita -> RSSType.Qiita(
                authorName = type.authorName,
            )

            is RSSCategoryGroupDetail.Zenn -> RSSType.Zenn(
                authorName = type.authorName,
                thumbnailUrl = type.thumbnailUrl,
            )

            is RSSCategoryGroupDetail.Hatena -> RSSType.Hatena(
                authorName = type.authorName,
                thumbnailUrl = type.thumbnailUrl,
            )

            is RSSCategoryGroupDetail.Other -> RSSType.Other(
                thumbnailUrl = type.thumbnailUrl,
            )
        },
        tag = tagIds,
        isRead = isRead,
        isFavorite = isFavorite,
        isBookmarked = isBookmarked,
        recommendScore = recommendScore,
        userRating = when (userRating) {
            com.khaki.room.entity.UserRating.Bad -> UserRating.Bad
            com.khaki.room.entity.UserRating.Good -> UserRating.Good
            com.khaki.room.entity.UserRating.None -> UserRating.None
        },
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
    userRating = when (userRating) {
        UserRating.Bad -> com.khaki.room.entity.UserRating.Bad
        UserRating.Good -> com.khaki.room.entity.UserRating.Good
        UserRating.None -> com.khaki.room.entity.UserRating.None
    }
)

private fun nowFallback(): LocalDateTime =
    runCatching { LocalDateTime.parse("1970-01-01T00:00:00") }.getOrThrow()

package com.khaki.repository

import com.khaki.modules.core.model.feed.FeedItem
import com.khaki.modules.core.model.feed.UserRating
import kotlinx.coroutines.flow.Flow

interface RssFeedRepository {

    fun getFeeds(): Flow<List<FeedItem>>

    suspend fun getFeed(id: String): FeedItem?

    suspend fun addFeeds(feeds: List<FeedItem>)

    suspend fun updateBookmark(id: String, isBookmarked: Boolean)

    suspend fun updateUserRating(id: String, userRating: UserRating)

    suspend fun doAsRead(id: String)

    suspend fun deleteAllFeeds()

}

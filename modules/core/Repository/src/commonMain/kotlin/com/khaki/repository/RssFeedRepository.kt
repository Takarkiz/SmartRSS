package com.khaki.repository

import com.khaki.modules.core.model.feed.FeedItem
import kotlinx.coroutines.flow.Flow

interface RssFeedRepository {

    fun getFeeds(): Flow<List<FeedItem>>

    suspend fun addFeed(feed: FeedItem)

    suspend fun addFeeds(feeds: List<FeedItem>)
}

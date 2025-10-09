package com.khaki.repository

import com.khaki.modules.core.model.feed.RSSFeed

interface HatenaFeedRSSRepository {

    // Fetch feeds by Hatena Blog userId (e.g., https://{userId}/feed)
    suspend fun feedsByUserId(userId: String): RSSFeed
}

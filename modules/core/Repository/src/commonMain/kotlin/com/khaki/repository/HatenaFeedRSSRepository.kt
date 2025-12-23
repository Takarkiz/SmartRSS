package com.khaki.repository

import com.khaki.modules.core.model.feed.RSSFeed

interface HatenaFeedRSSRepository {

    // Fetch feeds by Hatena Blog URL (e.g., {URL}/feed)
    suspend fun feedsByUrl(url: String): RSSFeed
}

package com.khaki.repository

import com.khaki.modules.core.model.feed.RSSFeed

interface ZennFeedRSSRepository {

    suspend fun popularFeeds(): RSSFeed

    suspend fun feedsByTag(tag: String): RSSFeed

    suspend fun feedsByUserId(userId: String): RSSFeed
    suspend fun feedsByUrl(url: String): RSSFeed
}

package com.khaki.repository

import com.khaki.modules.core.model.feed.RSSFeed

interface QiitaFeedRSSRepository {

    suspend fun popularFeeds(): RSSFeed
    
    suspend fun feedsByTag(tag: String): RSSFeed
    
    suspend fun feedsByUserId(userId: String): RSSFeed
}

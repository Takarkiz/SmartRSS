package com.khaki.repository

import com.khaki.modules.core.model.feed.RSSFeed

interface OtherFeedRssRepository {

    suspend fun feedsByUrl(url: String): RSSFeed
}

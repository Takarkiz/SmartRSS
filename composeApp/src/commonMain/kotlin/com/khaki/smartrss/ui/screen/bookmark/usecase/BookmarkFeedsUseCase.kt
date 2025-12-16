package com.khaki.smartrss.ui.screen.bookmark.usecase

import com.khaki.repository.RssFeedRepository
import com.khaki.modules.core.model.feed.FeedItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BookmarkFeedsUseCase(
    private val rssFeedRepository: RssFeedRepository,
) {
    val bookmarkFeeds: Flow<List<FeedItem>> = rssFeedRepository.getFeeds().map { feeds ->
        feeds.filter { it.isBookmarked }
    }

    suspend fun updateBookmark(feedId: String, isBookmark: Boolean) {
        rssFeedRepository.updateBookmark(feedId, isBookmark)
    }

    suspend fun doAsRead(id: String) {
        rssFeedRepository.doAsRead(id)
    }
}

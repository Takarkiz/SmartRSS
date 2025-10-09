package com.khaki.smartrss.ui.screen.recomend.usecase

import com.khaki.modules.core.model.feed.FeedItem
import com.khaki.repository.RssFeedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RecommendUseCase(
    private val rssFeedRepository: RssFeedRepository,
) {

    val allFeeds: Flow<List<FeedItem>> = rssFeedRepository
        .getFeeds()
        .map { feeds -> feeds.sortedByDescending { it.pubDate } }

    suspend fun updateBookmark(id: String) {
        val feed = rssFeedRepository.getFeed(id) ?: return
        val isBookmark = !feed.isBookmarked
        rssFeedRepository.updateBookmark(id, isBookmark)
    }
}

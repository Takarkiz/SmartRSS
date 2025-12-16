package com.khaki.smartrss.ui.screen.allfeeds.usecase

import com.khaki.modules.core.model.feed.FeedItem
import com.khaki.modules.core.model.feed.UserRating
import com.khaki.repository.RssFeedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AllFeedsUseCase(
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

    suspend fun updateGoodState(id: String) {
        val currentFeed = rssFeedRepository.getFeed(id) ?: return
        if (currentFeed.userRating == UserRating.Good) {
            rssFeedRepository.updateUserRating(id, UserRating.None)
        } else {
            rssFeedRepository.updateUserRating(id, UserRating.Good)
        }
    }

    suspend fun updateBadState(id: String) {
        val currentFeed = rssFeedRepository.getFeed(id) ?: return
        if (currentFeed.userRating == UserRating.Bad) {
            rssFeedRepository.updateUserRating(id, UserRating.None)
        } else {
            rssFeedRepository.updateUserRating(id, UserRating.Bad)
        }
    }

    suspend fun doAsRead(id: String) {
        rssFeedRepository.doAsRead(id)
    }
}

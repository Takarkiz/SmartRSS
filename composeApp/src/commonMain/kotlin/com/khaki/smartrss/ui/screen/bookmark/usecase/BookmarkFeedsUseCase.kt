package com.khaki.smartrss.ui.screen.bookmark.usecase

import com.khaki.modules.core.model.feed.FeedItem
import com.khaki.modules.core.model.feed.UserRating
import com.khaki.repository.RssFeedRepository
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

    suspend fun updateGoodState(id: String) {
        val feed = rssFeedRepository.getFeed(id) ?: return
        if (feed.userRating == UserRating.Good) {
            rssFeedRepository.updateUserRating(id, UserRating.None)
        } else {
            rssFeedRepository.updateUserRating(id, UserRating.Good)
        }
    }

    suspend fun updateBadState(id: String) {
        val feed = rssFeedRepository.getFeed(id) ?: return
        if (feed.userRating == UserRating.Bad) {
            rssFeedRepository.updateUserRating(id, UserRating.None)
        } else {
            rssFeedRepository.updateUserRating(id, UserRating.Bad)
        }
    }

    suspend fun doAsRead(id: String) {
        rssFeedRepository.doAsRead(id)
    }
}

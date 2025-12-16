package com.khaki.smartrss.ui.screen.allfeeds

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khaki.modules.core.model.feed.FeedItem
import com.khaki.smartrss.ext.toRelativeJaString
import com.khaki.smartrss.ui.screen.allfeeds.usecase.AllFeedsUseCase
import com.khaki.smartrss.ui.screen.feed.model.FeedItemUiModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AllFeedsViewModel(
    private val useCase: AllFeedsUseCase,
) : ViewModel() {

    companion object Companion {

        private const val TIMEOUT_MILLS = 5_000L
    }

    val uiState: StateFlow<AllFeedsUiState> = useCase.allFeeds.map { feeds ->
        AllFeedsUiState(
            feedItems = feeds.map {
                FeedItemUiModel(
                    id = it.id,
                    title = it.title,
                    description = it.description.replace("\n", " "),
                    link = it.link,
                    isBookmark = it.isBookmarked,
                    isRead = it.isRead,
                    pubDate = it.pubDate.toRelativeJaString(),
                    type = when (val rssType = it.rssType) {
                        is FeedItem.RSSType.Qiita -> FeedItemUiModel.RSSFeedType.Qiita
                        is FeedItem.RSSType.Zenn -> FeedItemUiModel.RSSFeedType.Zenn(
                            authorName = rssType.authorName,
                        )

                        is FeedItem.RSSType.Hatena -> FeedItemUiModel.RSSFeedType.Hatena(
                            authorName = rssType.authorName,
                        )

                        is FeedItem.RSSType.Other -> FeedItemUiModel.RSSFeedType.Other
                    },
                    userRating = FeedItemUiModel.Rating.Good,
                    thumbnailUrl = when (val rssType = it.rssType) {
                        is FeedItem.RSSType.Qiita -> null
                        is FeedItem.RSSType.Zenn -> rssType.thumbnailUrl
                        is FeedItem.RSSType.Hatena -> rssType.thumbnailUrl
                        is FeedItem.RSSType.Other -> rssType.thumbnailUrl
                    },
                )
            }
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLS),
        initialValue = AllFeedsUiState()
    )

    fun updateBookmarkState(feedId: String) {
        viewModelScope.launch {
            useCase.updateBookmark(feedId)
        }
    }

    fun updateGoodState(feedId: String) {
        viewModelScope.launch {
            // TODO: Implement good button click action in useCase
        }
    }

    fun updateBadState(feedId: String) {
        viewModelScope.launch {
            // TODO: Implement bad button click action in useCase
        }
    }

    fun doAsRead(feedId: String) {
        viewModelScope.launch {
            useCase.doAsRead(feedId)
        }
    }
}

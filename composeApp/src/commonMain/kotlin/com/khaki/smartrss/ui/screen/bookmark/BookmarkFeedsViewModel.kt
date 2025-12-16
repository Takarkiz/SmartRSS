package com.khaki.smartrss.ui.screen.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khaki.modules.core.model.feed.FeedItem
import com.khaki.smartrss.ext.toRelativeJaString
import com.khaki.smartrss.ui.screen.bookmark.usecase.BookmarkFeedsUseCase
import com.khaki.smartrss.ui.screen.feed.model.FeedItemUiModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class BookmarkFeedsViewModel(
    private val useCase: BookmarkFeedsUseCase,
) : ViewModel() {

    companion object Companion {

        private const val TIMEOUT_MILLS = 5_000L
    }

    val uiState: StateFlow<BookmarkFeedsUiState> = useCase.bookmarkFeeds.map { feeds ->
        BookmarkFeedsUiState(
            feedItems = feeds.map {
                FeedItemUiModel(
                    id = it.id,
                    title = it.title,
                    description = it.description.replace("\n", " "),
                    link = it.link,
                    isBookmark = it.isBookmarked,
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
        initialValue = BookmarkFeedsUiState()
    )

    fun updateBookmarkState(feedId: String) {
        viewModelScope.launch {
            val feed = uiState.value.feedItems.first { it.id == feedId }
            useCase.updateBookmark(feedId, !feed.isBookmark)
        }
    }
}

package com.khaki.smartrss.ui.screen.recomend

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khaki.modules.core.model.feed.FeedItem
import com.khaki.smartrss.ext.toRelativeJaString
import com.khaki.smartrss.ui.screen.recomend.model.FeedItemUiModel
import com.khaki.smartrss.ui.screen.recomend.usecase.RecommendUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RecommendViewModel(
    private val recommendUseCase: RecommendUseCase,
) : ViewModel() {

    companion object {

        private const val TIMEOUT_MILLS = 5_000L
    }

    val uiState: StateFlow<RecommendUiState> = recommendUseCase.allFeeds.map { feeds ->
        RecommendUiState(
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
        initialValue = RecommendUiState()
    )

    fun updateBookmarkState(feedId: String) {
        viewModelScope.launch {
            recommendUseCase.updateBookmark(feedId)
        }
    }
}

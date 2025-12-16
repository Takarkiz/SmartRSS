package com.khaki.smartrss.ui.screen.bookmark

import com.khaki.smartrss.ui.screen.feed.model.FeedItemUiModel

data class BookmarkFeedsUiState(
    val feedItems: List<FeedItemUiModel> = emptyList(),
)

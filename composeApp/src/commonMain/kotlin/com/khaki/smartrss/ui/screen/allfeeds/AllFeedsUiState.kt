package com.khaki.smartrss.ui.screen.allfeeds

import com.khaki.smartrss.ui.screen.feed.model.FeedItemUiModel

data class AllFeedsUiState(
    val feedItems: List<FeedItemUiModel> = emptyList(),
)

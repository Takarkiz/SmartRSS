package com.khaki.smartrss.ui.screen.allfeeds

import com.khaki.smartrss.ui.screen.recomend.model.FeedItemUiModel

data class AllFeedsUiState(
    val feedItems: List<FeedItemUiModel> = emptyList(),
)

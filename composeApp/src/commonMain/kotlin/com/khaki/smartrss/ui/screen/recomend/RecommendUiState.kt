package com.khaki.smartrss.ui.screen.recomend

import com.khaki.smartrss.ui.screen.recomend.model.FeedItemUiModel

data class RecommendUiState(
    val feedItems: List<FeedItemUiModel> = emptyList(),
)

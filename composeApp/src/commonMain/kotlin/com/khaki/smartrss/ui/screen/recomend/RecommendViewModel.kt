package com.khaki.smartrss.ui.screen.recomend

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khaki.smartrss.ext.toRelativeJaString
import com.khaki.smartrss.ui.screen.recomend.model.FeedItemUiModel
import com.khaki.smartrss.ui.screen.recomend.usecase.RecommendUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class RecommendViewModel(
    private val recommendUseCase: RecommendUseCase,
) : ViewModel() {

    val uiState: StateFlow<RecommendUiState> = recommendUseCase.allFeeds.map { feeds ->
        RecommendUiState(
            feedItems = feeds.map {
                FeedItemUiModel(
                    id = it.id,
                    title = it.title,
                    description = it.description,
                    link = it.link,
                    isBookmark = it.isBookmarked,
                    pubDate = it.pubDate.toRelativeJaString(),
                    faviconUrl = null,
                    thumbnailUrl = null,
                )
            }
        )

    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = RecommendUiState()
    )
}

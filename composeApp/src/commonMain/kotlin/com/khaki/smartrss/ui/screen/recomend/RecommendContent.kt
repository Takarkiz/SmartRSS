package com.khaki.smartrss.ui.screen.recomend

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.khaki.smartrss.ui.screen.recomend.composable.FeedItem

@Composable
fun RecommendContent(
    uiState: RecommendUiState,
    onClickItem: (String) -> Unit,
    onClickBookmark: (String) -> Unit,
    modifier: Modifier = Modifier,
) {

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
    ) {

        items(
            count = uiState.feedItems.size
        ) {
            FeedItem(
                item = uiState.feedItems[it],
                onClickItem = { url ->
                    onClickItem(url)
                },
                onClickBookmark = { id ->
                    onClickBookmark(id)
                }
            )
        }
    }
}

package com.khaki.smartrss.ui.screen.allfeeds

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.khaki.smartrss.ui.screen.feed.composable.FeedItem

@Composable
fun AllFeedsContent(
    uiState: AllFeedsUiState,
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

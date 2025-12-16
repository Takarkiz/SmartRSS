package com.khaki.smartrss.ui.screen.allfeeds

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.khaki.smartrss.ui.screen.feed.composable.FeedItem

@Composable
fun AllFeedsContent(
    uiState: AllFeedsUiState,
    onClickItem: (String, String) -> Unit,
    onClickBookmark: (String) -> Unit,
    modifier: Modifier = Modifier,
) {

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
    ) {
        items(
            items = uiState.feedItems,
            key = { it.id }
        ) { item ->
            FeedItem(
                item = item,
                onClickItem = { url ->
                    onClickItem(item.id, item.link)
                },
                onClickBookmark = { id ->
                    onClickBookmark(id)
                }
            )
        }
    }
}

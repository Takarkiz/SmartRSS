package com.khaki.smartrss.ui.screen.bookmark

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.khaki.smartrss.ui.screen.bookmark.composable.EmptyBookmarkFeeds
import com.khaki.smartrss.ui.screen.feed.composable.FeedItem

@Composable
fun BookmarkFeedsContent(
    uiState: BookmarkFeedsUiState,
    onClickItem: (String) -> Unit,
    onClickBookmark: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (uiState.feedItems.isEmpty()) {
        EmptyBookmarkFeeds(modifier = modifier)
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize()
        ) {
            items(uiState.feedItems) { item ->
                FeedItem(
                    item = item,
                    onClickItem = onClickItem,
                    onClickBookmark = onClickBookmark
                )
            }
        }
    }
}

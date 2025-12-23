package com.khaki.smartrss.ui.screen.bookmark

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.khaki.smartrss.ui.navigation.FeedClickArgs
import com.khaki.smartrss.ui.screen.bookmark.composable.EmptyBookmarkFeeds
import com.khaki.smartrss.ui.screen.feed.composable.FeedItem

@Composable
fun BookmarkFeedsContent(
    uiState: BookmarkFeedsUiState,
    onClickItem: (FeedClickArgs) -> Unit,
    onClickBookmark: (String) -> Unit,
    onClickGood: (String) -> Unit,
    onClickBad: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (uiState.feedItems.isEmpty()) {
        EmptyBookmarkFeeds(modifier = modifier)
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize()
        ) {
            items(
                items = uiState.feedItems,
                key = { it.id }
            ) { item ->
                FeedItem(
                    item = item,
                    onClickItem = {
                        onClickItem(
                            FeedClickArgs(
                                id = item.id,
                                url = item.link,
                                title = item.title
                            )
                        )
                    },
                    onClickBookmark = onClickBookmark,
                    onClickGood = onClickGood,
                    onClickBad = onClickBad,
                )
            }
        }
    }
}

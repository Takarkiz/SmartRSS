package com.khaki.smartrss.ui.screen.allfeeds

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.khaki.smartrss.ui.screen.feed.composable.FeedItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllFeedsContent(
    uiState: AllFeedsUiState,
    onRefresh: () -> Unit,
    onClickItem: (String, String) -> Unit,
    onClickBookmark: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val state = rememberPullToRefreshState()

    PullToRefreshBox(
        isRefreshing = uiState.isRefreshing,
        onRefresh = onRefresh,
        state = state,
        modifier = modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
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
}

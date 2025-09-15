package com.khaki.smartrss.ui.screen.recomend

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.khaki.smartrss.ui.screen.recomend.composable.FeedItem
import com.khaki.smartrss.ui.screen.recomend.model.FeedItemUiModel

@Composable
fun RecommendContent(
    feedItems: List<FeedItemUiModel>,
    onClickItem: (String) -> Unit,
    modifier: Modifier = Modifier,
) {

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
    ) {

        items(
            count = feedItems.size
        ) {
            FeedItem(
                item = feedItems[it],
                onClickItem = { id ->

                }
            )
        }
    }
}

package com.khaki.smartrss.ui.screen.recomend.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.khaki.smartrss.ui.screen.recomend.model.FeedItemUiModel
import com.khaki.smartrss.ui.screen.recomend.model.FeedItemUiModelPreviewProvider
import com.khaki.smartrss.ui.theme.SmartRssTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter

@Composable
fun FeedItem(
    item: FeedItemUiModel,
    onClickItem: (String) -> Unit,
    onClickBookmark: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = {
                    onClickItem(item.link)
                },
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(color = MaterialTheme.colorScheme.primary)
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {

        FeedItemTitle(
            title = item.title,
            isBookmark = item.isBookmark,
            typeIconResource = item.type.faviconResId,
            onClickBookmark = {
                onClickBookmark(item.id)
            }
        )

        FeedItemDetail(
            description = item.description,
            pubDate = item.pubDate,
            thumbnailUrl = item.thumbnailUrl,
        )

    }
}

@Preview
@Composable
private fun FeedItemPreview(
    @PreviewParameter(FeedItemUiModelPreviewProvider::class) item: FeedItemUiModel
) {
    SmartRssTheme {
        FeedItem(
            item = item,
            onClickItem = {},
            onClickBookmark = {},
        )
    }
}

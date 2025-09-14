package com.khaki.smartrss.ui.screen.recomend.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
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
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = {
                    onClickItem(item.id)
                },
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple()
            )
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(
                        1f,
                        fill = false
                    )
                )
                Icon(
                    imageVector = if (item.isBookmark) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                    contentDescription = if (item.isBookmark) "Remove bookmark" else "Add bookmark",
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = item.description,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = item.pubDate,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // ここにサムネイル画像を表示する
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
            onClickItem = {}
        )
    }
}

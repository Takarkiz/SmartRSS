package com.khaki.smartrss.ui.screen.feed.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.khaki.smartrss.ui.screen.feed.model.FeedItemUiModel
import com.khaki.smartrss.ui.screen.feed.model.FeedItemUiModelPreviewProvider
import com.khaki.smartrss.ui.theme.SmartRssTheme
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter

@Composable
fun FeedItemTitle(
    title: String,
    isBookmark: Boolean,
    typeIconResource: DrawableResource,
    onClickBookmark: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {

        Column(
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {

            Image(
                modifier = Modifier.height(24.dp),
                painter = painterResource(typeIconResource),
                contentDescription = "favicon_icon_image",
            )

            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )

        }

        IconButton(
            onClick = {
                onClickBookmark()
            }
        ) {

            Icon(
                imageVector = if (isBookmark) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                contentDescription = if (isBookmark) "Remove bookmark" else "Add bookmark",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview
@Composable
private fun PreviewFeedItemTitle(
    @PreviewParameter(FeedItemUiModelPreviewProvider::class) item: FeedItemUiModel
) {
    SmartRssTheme {
        FeedItemTitle(
            title = item.title,
            isBookmark = item.isBookmark,
            typeIconResource = item.type.faviconResId,
            onClickBookmark = {},
        )
    }
}

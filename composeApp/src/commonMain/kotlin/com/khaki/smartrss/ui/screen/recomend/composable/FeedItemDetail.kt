package com.khaki.smartrss.ui.screen.recomend.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.khaki.smartrss.ui.screen.recomend.model.FeedItemUiModel
import com.khaki.smartrss.ui.screen.recomend.model.FeedItemUiModelPreviewProvider
import com.khaki.smartrss.ui.theme.SmartRssTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter

@Composable
fun FeedItemDetail(
    description: String,
    pubDate: String,
    thumbnailUrl: String?,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier,
    ) {

        Column(
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {

            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Text(
                text = pubDate,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        if (thumbnailUrl != null) {
            AsyncImage(
                model = thumbnailUrl,
                contentDescription = "thumbnail_image",
                modifier = Modifier.height(24.dp),
            )
        }
    }
}

@Preview
@Composable
private fun PreviewFeedItemDetail(
    @PreviewParameter(FeedItemUiModelPreviewProvider::class) item: FeedItemUiModel
) {
    SmartRssTheme {
        FeedItemDetail(
            description = item.description,
            pubDate = item.pubDate,
            thumbnailUrl = item.thumbnailUrl,
        )
    }
}

package com.khaki.smartrss.ui.screen.feed.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.ThumbDown
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.khaki.smartrss.ui.screen.feed.model.FeedItemUiModel
import com.khaki.smartrss.ui.screen.feed.model.FeedItemUiModelPreviewProvider
import com.khaki.smartrss.ui.theme.SmartRssTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter

@Composable
fun FeedItemDetail(
    description: String,
    pubDate: String,
    rating: FeedItemUiModel.Rating,
    thumbnailUrl: String?,
    onClickGood: () -> Unit,
    onClickBad: () -> Unit,
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

            Row {

                Text(
                    text = pubDate,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    FilledIconButton(
                        colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = if (rating == FeedItemUiModel.Rating.Good) {
                                MaterialTheme.colorScheme.surfaceVariant
                            } else {
                                Color.Transparent
                            }
                        ),
                        onClick = { onClickGood() }
                    ) {
                        Icon(
                            imageVector = if (rating == FeedItemUiModel.Rating.Good) {
                                Icons.Filled.ThumbUp
                            } else {
                                Icons.Outlined.ThumbUp
                            },
                            tint = MaterialTheme.colorScheme.onSurface,
                            contentDescription = "Good",
                        )
                    }
                    FilledIconButton(
                        colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = if (rating == FeedItemUiModel.Rating.Bad) {
                                MaterialTheme.colorScheme.surfaceVariant
                            } else {
                                Color.Transparent
                            }
                        ),
                        onClick = { onClickBad() }
                    ) {
                        Icon(
                            imageVector = if (rating == FeedItemUiModel.Rating.Bad) {
                                Icons.Filled.ThumbDown
                            } else {
                                Icons.Outlined.ThumbDown
                            },
                            tint = MaterialTheme.colorScheme.onSurface,
                            contentDescription = "Bad",
                        )
                    }
                }
            }

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
            rating = item.userRating,
            onClickGood = {},
            onClickBad = {},
        )
    }
}

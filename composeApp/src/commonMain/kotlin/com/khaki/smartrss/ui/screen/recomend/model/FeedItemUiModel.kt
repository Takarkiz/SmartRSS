package com.khaki.smartrss.ui.screen.recomend.model

import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider

data class FeedItemUiModel(
    val id: String,
    val title: String,
    val description: String,
    val link: String,
    val isBookmark: Boolean,
    val pubDate: String,
    val faviconUrl: String?,
    val thumbnailUrl: String?,
)

internal class FeedItemUiModelPreviewProvider: PreviewParameterProvider<FeedItemUiModel> {
    override val values: Sequence<FeedItemUiModel>
        get() = sequenceOf(
            FeedItemUiModel(
                id = "1",
                title = "Sample Title 1",
                description = "This is a sample description for item 1.",
                link = "https://example.com/1",
                isBookmark = false,
                pubDate = "2023-10-01",
                faviconUrl = null,
                thumbnailUrl = null,
            ),
            FeedItemUiModel(
                id = "2",
                title = "Sample Title 2",
                description = "This is a sample description for item 2. It has a bit more text to show how it looks with longer content.",
                link = "https://example.com/2",
                isBookmark = true,
                pubDate = "2023-10-02",
                faviconUrl = null,
                thumbnailUrl = null,
            )
        )
}
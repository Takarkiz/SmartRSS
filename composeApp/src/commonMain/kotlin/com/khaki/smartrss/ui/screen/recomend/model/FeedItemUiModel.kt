package com.khaki.smartrss.ui.screen.recomend.model

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider
import smartrss.composeapp.generated.resources.Res
import smartrss.composeapp.generated.resources.favicon_hatena
import smartrss.composeapp.generated.resources.favicon_qiita
import smartrss.composeapp.generated.resources.favicon_rss
import smartrss.composeapp.generated.resources.favicon_zenn

data class FeedItemUiModel(
    val id: String,
    val title: String,
    val description: String,
    val link: String,
    val isBookmark: Boolean,
    val pubDate: String,
    val type: RSSFeedType,
    val thumbnailUrl: String?,
) {

    sealed class RSSFeedType {

        abstract val faviconResId: DrawableResource

        data object Qiita : RSSFeedType() {
            override val faviconResId: DrawableResource
                get() = Res.drawable.favicon_qiita
        }

        data class Zenn(
            val authorName: String,
            val thumbnailUrl: String?,
        ) : RSSFeedType() {
            override val faviconResId: DrawableResource
                get() = Res.drawable.favicon_zenn
        }

        data class Hatena(
            val authorName: String,
            val thumbnailUrl: String?,
        ) : RSSFeedType() {
            override val faviconResId: DrawableResource
                get() = Res.drawable.favicon_hatena
        }

        data class Other(
            val thumbnailUrl: String?,
        ) : RSSFeedType() {
            override val faviconResId: DrawableResource
                get() = Res.drawable.favicon_rss
        }
    }
}

internal class FeedItemUiModelPreviewProvider : PreviewParameterProvider<FeedItemUiModel> {
    override val values: Sequence<FeedItemUiModel>
        get() = sequenceOf(
            FeedItemUiModel(
                id = "1",
                title = "Sample Title 1",
                description = "This is a sample description for item 1.",
                link = "https://example.com/1",
                isBookmark = false,
                pubDate = "2023-10-01",
                type = FeedItemUiModel.RSSFeedType.Qiita,
                thumbnailUrl = null,
            ),
            FeedItemUiModel(
                id = "2",
                title = "Sample Title 2",
                description = "This is a sample description for item 2. It has a bit more text to show how it looks with longer content.",
                link = "https://example.com/2",
                isBookmark = true,
                pubDate = "2023-10-02",
                type = FeedItemUiModel.RSSFeedType.Qiita,
                thumbnailUrl = null,
            )
        )
}

package com.khaki.modules.core.model.feed

import kotlinx.datetime.LocalDateTime

data class FeedItem(
    val id: String,
    val title: String,
    val link: String,
    val pubDate: LocalDateTime,
    val description: String,
    val rssType: RSSType,
    val tag: List<String> = emptyList(),
    val isRead: Boolean = false,
    val isFavorite: Boolean = false,
    val isBookmarked: Boolean = false,
    val recommendScore: Int = 0,
) {

    sealed interface RSSType {

        data class Qiita(
            val authorName: String,
        ) : RSSType

        data class Zenn(
            val authorName: String,
            val thumbnailUrl: String?
        ) : RSSType

        data class Hatena(
            val authorName: String,
            val thumbnailUrl: String?,
        ) : RSSType

        data class Other(
            val thumbnailUrl: String?,
        ) : RSSType
    }
}

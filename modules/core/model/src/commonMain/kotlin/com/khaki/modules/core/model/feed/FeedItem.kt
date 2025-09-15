package com.khaki.modules.core.model.feed

import kotlinx.datetime.LocalDateTime

data class FeedItem(
    val title: String,
    val link: String,
    val pubDate: LocalDateTime,
    val description: String,
    val rssType: RSSType,
) {

    sealed interface RSSType {

        data class Qiita(
            val authorName: String,
            val updatedDate: LocalDateTime,
        ) : RSSType

        data class Zenn(
            val authorName: String,
            val thumbnailUrl: String?
        ) : RSSType

        data class Hatena(
            val authorName: String,
            val thumbnailUrl: String?,
            val updatedDate: LocalDateTime,
        ) : RSSType

        data class Other(
            val thumbnailUrl: String?,
        ) : RSSType
    }
}

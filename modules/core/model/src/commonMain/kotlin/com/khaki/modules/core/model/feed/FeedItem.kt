package com.khaki.modules.core.model.feed

import kotlinx.datetime.LocalDateTime

data class FeedItem(
    val title: String,
    val link: String,
    val pubDate: LocalDateTime,
    val description: String,
) {

    sealed interface RSSType {

        data class Qiita(
            val articleId: String,
            val authorName: String,
        )

        data class Zenn(
            val authorName: String,
            val thumbnailUrl: String?
        )

        data class Hatena(
            val authorName: String,
            val thumbnailUrl: String?,
            val updatedDate: LocalDateTime,
        )

        data class Other(
            val thumbnailUrl: String?,
        )
    }
}

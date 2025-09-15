package com.khaki.modules.core.model.feed

import kotlinx.datetime.LocalDateTime

data class RSSFeed(
    val title: String,
    val link: String,
    val description: String,
    val language: String,
    val lastBuildDate: LocalDateTime,
    val items: List<FeedItem>,
)

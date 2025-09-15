package com.khaki.modules.core.model.feed

data class RSSFeed(
    val title: String,
    val link: String,
    val description: String,
    val items: List<FeedItem>,
)

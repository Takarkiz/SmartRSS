package com.khaki.repository.impl

import com.khaki.api.HatenaRssApi
import com.khaki.api.dto.HatenaRssFeedDto
import com.khaki.modules.core.model.feed.RSSFeed
import com.khaki.repository.HatenaFeedRSSRepository
import kotlinx.datetime.toLocalDateTime
import nl.adaptivity.xmlutil.ExperimentalXmlUtilApi
import nl.adaptivity.xmlutil.serialization.XML

class HatenaFeedRSSRepositoryImpl(
    private val hatenaRssApi: HatenaRssApi,
) : HatenaFeedRSSRepository {

    @OptIn(ExperimentalXmlUtilApi::class)
    private val xml = XML { ignoreUnknownNames() }

    override suspend fun feedsByUserId(userId: String): RSSFeed {
        val xmlString = hatenaRssApi.getFeedsByUserId(userId)
        val dto = xml.decodeFromString(HatenaRssFeedDto.serializer(), xmlString)
        return dto.toRssFeed()
    }
}

private fun HatenaRssFeedDto.toRssFeed(): RSSFeed {
    return RSSFeed(
        title = title,
        link = links.firstOrNull { it.rel == null }?.href ?: "",
        description = subtitle ?: "",
        items = entries.map { it.toRssFeedItem() }
    )
}

private fun HatenaRssFeedDto.Entry.toRssFeedItem(): RSSFeed.Item {
    return RSSFeed.Item(
        id = id,
        title = title,
        link = links.firstOrNull { it.rel == "alternate" }?.href ?: links.firstOrNull()?.href ?: "",
        description = summary?.value ?: "",
        pubDate = published?.let {
            kotlinx.datetime.Instant.parse(it).toLocalDateTime(kotlinx.datetime.TimeZone.UTC)
        },
        author = author?.name ?: "",
        isBookmarked = false
    )
}

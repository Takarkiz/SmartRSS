package com.khaki.repositoryimpl

import com.khaki.api.dto.QiitaRssFeedDto
import com.khaki.api.service.RSSApiService
import com.khaki.modules.core.model.feed.FeedItem
import com.khaki.modules.core.model.feed.RSSFeed
import com.khaki.repository.QiitaFeedRSSRepository
import kotlinx.datetime.LocalDateTime

class QiitaFeedRSSRepositoryImpl(
    private val apiService: RSSApiService
) : QiitaFeedRSSRepository {
    override suspend fun popularFeeds(): RSSFeed {
        val response = apiService.fetchQiitaRssFeed("https://qiita.com/popular-items/feed.atom")
        return mapToDomain(response)
    }

    override suspend fun feedsByTag(tag: String): RSSFeed {
        val response = apiService.fetchQiitaRssFeed("https://qiita.com/tags/$tag/feed.atom")
        return mapToDomain(response)
    }

    override suspend fun feedsByUserId(userId: String): RSSFeed {
        val response = apiService.fetchQiitaRssFeed("https://qiita.com/$userId/feed.atom")
        return mapToDomain(response)
    }

    private fun mapToDomain(dto: QiitaRssFeedDto): RSSFeed {
        return RSSFeed(
            title = dto.title,
            link = dto.link.getOrElse(0) { throw IllegalStateException("Link is empty") }.href,
            description = dto.description,
            items = dto.entry.map { entry ->
                FeedItem(
                    title = entry.title,
                    link = entry.link.getOrElse(0) { throw IllegalStateException("Link is empty") }.href,
                    description = entry.description.type,
                    pubDate = LocalDateTime.parse(entry.pubDate),
                    rssType = FeedItem.RSSType.Qiita(
                        authorName = entry.author.name,
                        updatedDate = LocalDateTime.parse(entry.updatedDate)
                    )
                )
            }
        )
    }
}

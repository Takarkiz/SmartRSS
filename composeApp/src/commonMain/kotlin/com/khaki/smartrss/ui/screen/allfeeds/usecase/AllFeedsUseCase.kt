package com.khaki.smartrss.ui.screen.allfeeds.usecase

import com.khaki.modules.core.model.feed.FeedItem
import com.khaki.modules.core.model.feed.Popular
import com.khaki.modules.core.model.feed.RssCategory
import com.khaki.modules.core.model.feed.Tag
import com.khaki.modules.core.model.feed.URL
import com.khaki.modules.core.model.feed.UserId
import com.khaki.modules.core.model.feed.UserRating
import com.khaki.repository.HatenaFeedRSSRepository
import com.khaki.repository.OtherFeedRssRepository
import com.khaki.repository.QiitaFeedRSSRepository
import com.khaki.repository.RssCategoryRepository
import com.khaki.repository.RssFeedRepository
import com.khaki.repository.ZennFeedRSSRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class AllFeedsUseCase(
    private val rssFeedRepository: RssFeedRepository,
    private val qiitaFeedsRssRepository: QiitaFeedRSSRepository,
    private val zennFeedsRssRepository: ZennFeedRSSRepository,
    private val hatenaFeedsRssRepository: HatenaFeedRSSRepository,
    private val otherFeedsRssRepository: OtherFeedRssRepository,
    private val rssCategoryRepository: RssCategoryRepository,
) {

    val allFeeds: Flow<List<FeedItem>> = rssFeedRepository
        .getFeeds()
        .map { feeds -> feeds.sortedByDescending { it.pubDate } }

    suspend fun refreshFeeds() {
        val categories = try {
            rssCategoryRepository.getAllCategories().first()
        } catch (e: Exception) {
            return // Failed to read categories
        }

        categories.forEach { category ->
            runCatching { refreshFeedForCategory(category) }
        }
    }

    private suspend fun refreshFeedForCategory(category: RssCategory) {
        val feed = when (category.type) {
            RssCategory.RSSGroupType.Qiita -> when (val form = category.formType) {
                is UserId -> qiitaFeedsRssRepository.feedsByUserId(form.value)
                is Tag -> qiitaFeedsRssRepository.feedsByTag(form.value)
                is Popular -> qiitaFeedsRssRepository.popularFeeds()
                else -> null
            }

            RssCategory.RSSGroupType.Zenn -> when (val form = category.formType) {
                is UserId -> zennFeedsRssRepository.feedsByUserId(form.value)
                is Tag -> zennFeedsRssRepository.feedsByTag(form.value)
                is Popular -> zennFeedsRssRepository.popularFeeds()
                is URL -> zennFeedsRssRepository.feedsByUrl(form.value)
                else -> null
            }

            RssCategory.RSSGroupType.HatenaBlog -> when (val form = category.formType) {
                is UserId -> hatenaFeedsRssRepository.feedsByUserId(form.value)
                is URL -> hatenaFeedsRssRepository.feedsByUrl(form.value)
                else -> null
            }

            RssCategory.RSSGroupType.Others -> when (val form = category.formType) {
                is URL -> otherFeedsRssRepository.feedsByUrl(form.value)
                else -> null
            }

            else -> null // Github and others are not supported
        }

        feed?.items?.let { rssFeedRepository.addFeeds(it) }
    }

    suspend fun updateBookmark(id: String) {
        val feed = rssFeedRepository.getFeed(id) ?: return
        val isBookmark = !feed.isBookmarked
        rssFeedRepository.updateBookmark(id, isBookmark)
    }

    suspend fun updateGoodState(id: String) {
        val currentFeed = rssFeedRepository.getFeed(id) ?: return
        if (currentFeed.userRating == UserRating.Good) {
            rssFeedRepository.updateUserRating(id, UserRating.None)
        } else {
            rssFeedRepository.updateUserRating(id, UserRating.Good)
        }
    }

    suspend fun updateBadState(id: String) {
        val currentFeed = rssFeedRepository.getFeed(id) ?: return
        if (currentFeed.userRating == UserRating.Bad) {
            rssFeedRepository.updateUserRating(id, UserRating.None)
        } else {
            rssFeedRepository.updateUserRating(id, UserRating.Bad)
        }
    }

    suspend fun doAsRead(id: String) {
        rssFeedRepository.doAsRead(id)
    }
}

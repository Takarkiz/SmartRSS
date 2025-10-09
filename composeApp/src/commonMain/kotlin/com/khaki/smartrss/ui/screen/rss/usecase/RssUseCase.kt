package com.khaki.smartrss.ui.screen.rss.usecase

import com.khaki.modules.core.model.feed.FormType
import com.khaki.modules.core.model.feed.Popular
import com.khaki.modules.core.model.feed.RSSFeed
import com.khaki.modules.core.model.feed.RssCategory
import com.khaki.modules.core.model.feed.Tag
import com.khaki.modules.core.model.feed.URL
import com.khaki.modules.core.model.feed.UserId
import com.khaki.repository.HatenaFeedRSSRepository
import com.khaki.repository.OtherFeedRssRepository
import com.khaki.repository.QiitaFeedRSSRepository
import com.khaki.repository.RssCategoryRepository
import com.khaki.repository.RssFeedRepository
import com.khaki.repository.ZennFeedRSSRepository
import com.khaki.smartrss.ui.Result
import kotlinx.coroutines.flow.Flow
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class RssUseCase(
    private val qiitaFeedsRssRepository: QiitaFeedRSSRepository,
    private val zennFeedsRssRepository: ZennFeedRSSRepository,
    private val hatenaFeedsRssRepository: HatenaFeedRSSRepository,
    private val otherFeedsRssRepository: OtherFeedRssRepository,
    private val rssCategoryRepository: RssCategoryRepository,
    private val rssFeedRepository: RssFeedRepository,
) {

    val followingCategories: Flow<List<RssCategory>> =
        rssCategoryRepository.getAllFollowingCategories()

    /**
     * QiitaのRSSフィードが有効であるかどうかを判断し、RSSフィードとして追加をする
     *
     * 1. QiitaのユーザーIDまたは組織IDが有効であるかを確認する
     * 2. 確認する際に取得したフィードは最新のフィードとして追加する
     * 3. 有効であれば、QiitaのRSSフィードURLを生成し、Roomデータベースに追加する
     */
    @OptIn(ExperimentalUuidApi::class)
    suspend fun checkAndAddQiitaRssFeed(form: FormType): Result<Boolean, RssAppendingError> {
        val rssFeed = try {
            when (form) {
                is UserId -> qiitaFeedsRssRepository.feedsByUserId(form.value)
                is Tag -> qiitaFeedsRssRepository.feedsByTag(form.value)
                is Popular -> qiitaFeedsRssRepository.popularFeeds()
                else -> return Result.Error(RssAppendingError.IllegalInputFormat)
            }
        } catch (e: Exception) {
            return Result.Error(RssAppendingError.FetchingFailed(e))
        }
        return processFetchedFeed(rssFeed, RssCategory.RSSGroupType.Qiita, form)
    }

    @OptIn(ExperimentalUuidApi::class)
    suspend fun checkAndAddZennRssFeed(form: FormType): Result<Boolean, RssAppendingError> {
        val rssFeed = try {
            when (form) {
                is UserId -> zennFeedsRssRepository.feedsByUserId(form.value)
                is Tag -> zennFeedsRssRepository.feedsByTag(form.value)
                is Popular -> zennFeedsRssRepository.popularFeeds()
                else -> return Result.Error(RssAppendingError.IllegalInputFormat)
            }
        } catch (e: Exception) {
            return Result.Error(RssAppendingError.FetchingFailed(e))
        }
        return processFetchedFeed(rssFeed, RssCategory.RSSGroupType.Zenn, form)
    }

    suspend fun checkAndAddHatenaRssFeed(form: UserId): Result<Boolean, RssAppendingError> {
        val rssFeed = try {
            hatenaFeedsRssRepository.feedsByUserId(form.value)
        } catch (e: Exception) {
            return Result.Error(RssAppendingError.FetchingFailed(e))
        }

        return processFetchedFeed(
            rssFeed = rssFeed,
            groupType = RssCategory.RSSGroupType.HatenaBlog,
            form = form
        )
    }

    suspend fun checkAndAddOtherRssFeed(url: URL): Result<Boolean, RssAppendingError> {
        val rssFeed = try {
            otherFeedsRssRepository.feedsByUrl(url.value)
        } catch (e: Exception) {
            return Result.Error(RssAppendingError.FetchingFailed(e))
        }

        return processFetchedFeed(
            rssFeed = rssFeed,
            groupType = RssCategory.RSSGroupType.Others,
            form = url
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    private suspend fun processFetchedFeed(
        rssFeed: RSSFeed,
        groupType: RssCategory.RSSGroupType,
        form: FormType,
    ): Result<Boolean, RssAppendingError> {
        if (rssFeed.items.isEmpty()) {
            return Result.Error(RssAppendingError.NotFoundFeed)
        }

        // Try to persist the fetched items. Even if it fails, continue to add the category entry.
        runCatching { rssFeedRepository.addFeeds(rssFeed.items) }

        // Check duplication by URL
        val hasDuplicate = try {
            rssCategoryRepository.doesUrlExist(rssFeed.link)
        } catch (e: Exception) {
            return Result.Error(RssAppendingError.RoomDatabaseError(e))
        }
        if (hasDuplicate) return Result.Error(RssAppendingError.DuplicateRssCategory)

        // Insert category
        try {
            rssCategoryRepository.insertRssCategory(
                RssCategory(
                    id = Uuid.random().toString(),
                    name = rssFeed.title,
                    description = rssFeed.description,
                    following = true,
                    url = rssFeed.link,
                    type = groupType,
                    formType = form,
                )
            )
        } catch (e: Exception) {
            return Result.Error(RssAppendingError.RoomDatabaseError(e))
        }

        return Result.Success(true)
    }
}

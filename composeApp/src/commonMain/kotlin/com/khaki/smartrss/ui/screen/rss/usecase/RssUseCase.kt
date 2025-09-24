package com.khaki.smartrss.ui.screen.rss.usecase

import com.khaki.modules.core.model.feed.FormType
import com.khaki.modules.core.model.feed.Popular
import com.khaki.modules.core.model.feed.RssCategory
import com.khaki.modules.core.model.feed.Tag
import com.khaki.modules.core.model.feed.UserId
import com.khaki.repository.QiitaFeedRSSRepository
import com.khaki.repository.RssCategoryRepository
import com.khaki.smartrss.ui.Result
import kotlinx.coroutines.flow.Flow
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class RssUseCase(
    private val qiitaFeedsRssRepository: QiitaFeedRSSRepository,
    private val rssCategoryRepository: RssCategoryRepository,
) {

    val followingCategories: Flow<List<RssCategory>> =
        rssCategoryRepository.getAllFollowingCategories()

    /**
     * QiitaのRSSフィードが有効であるかどうかを判断し、RSSフィードとして追加をする
     *
     * 1. QiitaのユーザーIDまたは組織IDが有効であるかを確認する
     * 2. 有効であれば、QiitaのRSSフィードURLを生成し、Roomデータベースに追加する
     * 3. 確認する際に取得したフィードは最新のフィードとして追加する
     */
    @OptIn(ExperimentalUuidApi::class)
    suspend fun chackAndAddQiitaRssFeed(form: FormType): Result<Boolean, RssAppendingError> {
        val rssFeed = try {
            when (form) {
                is UserId -> {
                    qiitaFeedsRssRepository.feedsByUserId(form.value)
                }

                is Tag -> {
                    qiitaFeedsRssRepository.feedsByTag(form.value)
                }

                is Popular -> {
                    qiitaFeedsRssRepository.popularFeeds()
                }

                else -> {
                    return Result.Error(RssAppendingError.IllegalInputFormat)
                }
            }
        } catch (e: Exception) {
            return Result.Error(RssAppendingError.FetchingFailed(e))
        }

        if (rssFeed.items.isEmpty()) {
            return Result.Error(RssAppendingError.NotFoundFeed)
        }

        try {
            val hasDuplicate = rssCategoryRepository.doesUrlExist(rssFeed.link)
            if (hasDuplicate) {
                return Result.Error(RssAppendingError.DuplicateRssCategory)
            }
        } catch (e: Exception) {
            return Result.Error(RssAppendingError.RoomDatabaseError(e))
        }

        try {
            rssCategoryRepository.insertRssCategory(
                RssCategory(
                    id = Uuid.random().toString(),
                    name = rssFeed.title,
                    description = rssFeed.description,
                    following = true,
                    url = rssFeed.link,
                    type = RssCategory.RSSGroupType.Qiita,
                    formType = form,
                )
            )
        } catch (e: Exception) {
            return Result.Error(RssAppendingError.RoomDatabaseError(e))
        }

        return Result.Success(true)
    }

    suspend fun checkAndAddZennRssFeed(form: FormType) {
        // ZennのRSSフィード追加ロジックをここに実装
    }
}

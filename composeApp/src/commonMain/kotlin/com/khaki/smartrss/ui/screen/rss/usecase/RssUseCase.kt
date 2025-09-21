package com.khaki.smartrss.ui.screen.rss.usecase

import com.khaki.modules.core.model.feed.FormType
import com.khaki.modules.core.model.feed.Popular
import com.khaki.modules.core.model.feed.Tag
import com.khaki.modules.core.model.feed.UserId
import com.khaki.repository.QiitaFeedRSSRepository
import com.khaki.smartrss.ui.Result

class RssUseCase(
    private val qiitaFeedsRssRepository: QiitaFeedRSSRepository,
) {

    /**
     * QiitaのRSSフィードが有効であるかどうかを判断し、RSSフィードとして追加をする
     *
     * 1. QiitaのユーザーIDまたは組織IDが有効であるかを確認する
     * 2. 有効であれば、QiitaのRSSフィードURLを生成し、Roomデータベースに追加する
     * 3. 確認する際に取得したフィードは最新のフィードとして追加する
     */
    suspend fun chackAndAddQiitaRssFeed(form: FormType): Result<Boolean, RssAppendingError> {
        val feed = try {
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

        return Result.Success(true)

    }

    suspend fun checkAndAddZennRssFeed(form: FormType) {
        // ZennのRSSフィード追加ロジックをここに実装
    }
}

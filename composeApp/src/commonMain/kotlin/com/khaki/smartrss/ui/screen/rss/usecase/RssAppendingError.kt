package com.khaki.smartrss.ui.screen.rss.usecase

sealed interface RssAppendingError {

    /**
     * 入力した値が不正な場合に発生するエラー
     */
    data object IllegalInputFormat : RssAppendingError

    /**
     * 既に登録されているRSSの種別の場合に発生するエラー
     */
    data object DuplicateRssCategory : RssAppendingError

    /**
     * RSSフィードの取得に失敗した場合に発生するエラー
     */
    data object NotFoundFeed : RssAppendingError

    /**
     * 未実装の場合
     */

    data object NotImplemented : RssAppendingError

    /**
     * RSSの取得に失敗した場合
     */
    data class FetchingFailed(
        val error: Throwable
    ) : RssAppendingError

    /**
     * Roomデータベースのエラーが発生した場合
     */
    data class RoomDatabaseError(
        val error: Throwable
    ) : RssAppendingError
}

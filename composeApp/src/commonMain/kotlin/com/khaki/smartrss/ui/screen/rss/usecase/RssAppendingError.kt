package com.khaki.smartrss.ui.screen.rss.usecase

sealed interface RssAppendingError {

    data object IllegalInputFormat : RssAppendingError

    data object DuplicateRssCategory : RssAppendingError

    data object NotFoundFeed : RssAppendingError

    data class FetchingFailed(
        val error: Throwable
    ) : RssAppendingError

    data class RoomDatabaseError(
        val error: Throwable
    ) : RssAppendingError
}

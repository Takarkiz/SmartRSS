package com.khaki.smartrss.ui.screen.rss.usecase

sealed interface RssAppendingError {

    data object IllegalInputFormat : RssAppendingError

    data class FetchingFailed(
        val error: Throwable
    ) : RssAppendingError
}

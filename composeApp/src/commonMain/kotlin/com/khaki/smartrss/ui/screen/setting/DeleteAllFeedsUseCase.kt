package com.khaki.smartrss.ui.screen.setting

import com.khaki.repository.RssFeedRepository

class DeleteAllFeedsUseCase(
    private val rssFeedRepository: RssFeedRepository,
) {
    suspend fun deleteAll() {
        rssFeedRepository.deleteAllFeeds()
    }
}

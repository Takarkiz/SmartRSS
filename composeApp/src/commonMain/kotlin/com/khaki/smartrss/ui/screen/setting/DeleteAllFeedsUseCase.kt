package com.khaki.smartrss.ui.screen.setting

import com.khaki.repository.RssFeedRepository

class DeleteAllFeedsUseCase(
    private val rssFeedRepository: RssFeedRepository,
) {
    suspend operator fun invoke() {
        rssFeedRepository.deleteAllFeeds()
    }
}

package com.khaki.smartrss.ui.screen.setting

import com.khaki.repository.RssFeedRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class DeleteAllFeedsUseCaseTest {

    private val rssFeedRepository = mockk<RssFeedRepository>(relaxed = true)
    private val useCase = DeleteAllFeedsUseCase(rssFeedRepository)

    @Test
    fun `invoke calls deleteAllFeeds on the repository`() = runTest {
        // When
        useCase()

        // Then
        coVerify(exactly = 1) { rssFeedRepository.deleteAllFeeds() }
    }
}

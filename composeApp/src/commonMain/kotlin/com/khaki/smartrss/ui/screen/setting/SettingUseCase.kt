package com.khaki.smartrss.ui.screen.setting

import com.khaki.modules.core.model.feed.Setting
import com.khaki.repository.SettingRepository
import kotlinx.coroutines.flow.Flow

class SettingUseCase(
    private val settingRepository: SettingRepository,
) {
    val setting: Flow<Setting> = settingRepository.setting

    suspend fun updateSummarySetting(isDetailSummary: Boolean) {
        settingRepository.updateSummarySetting(isDetailSummary)
    }
}

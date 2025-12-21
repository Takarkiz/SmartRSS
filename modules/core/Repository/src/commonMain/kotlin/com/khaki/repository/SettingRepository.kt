package com.khaki.repository

import com.khaki.modules.core.model.feed.Setting
import kotlinx.coroutines.flow.Flow

interface SettingRepository {

    val setting: Flow<Setting>

    suspend fun updateSummarySetting(isDetailSummary: Boolean)

}

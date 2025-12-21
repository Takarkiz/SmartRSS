package com.khaki.repositoryimpl

import com.khaki.datastore.setting.SettingDataStore
import com.khaki.modules.core.model.feed.Setting
import com.khaki.repository.SettingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingRepositoryImpl(
    private val datastore: SettingDataStore,
) : SettingRepository {

    override val setting: Flow<Setting> = datastore.setting.map { setting ->
        Setting(
            isDetailSummary = setting.shouldShowDetailSummary
        )
    }

    override suspend fun updateSummarySetting(isDetailSummary: Boolean) {
        datastore.updateSetting(
            com.khaki.datastore.setting.model.Setting(
                shouldShowDetailSummary = isDetailSummary
            )
        )
    }
}

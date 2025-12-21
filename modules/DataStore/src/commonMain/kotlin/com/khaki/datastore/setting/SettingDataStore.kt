package com.khaki.datastore.setting

import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.okio.OkioStorage
import com.khaki.datastore.setting.model.Setting
import com.khaki.datastore.setting.serializer.SettingJsonSerializer
import kotlinx.coroutines.flow.Flow
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.SYSTEM

class SettingDataStore(
    private val produceFilePath: () -> String,
) {

    private val db = DataStoreFactory.create(
        storage = OkioStorage<Setting>(
            fileSystem = FileSystem.SYSTEM,
            serializer = SettingJsonSerializer,
            producePath = {
                produceFilePath().toPath()
            }
        )
    )

    val setting: Flow<Setting> get() = db.data

    suspend fun updateSetting(setting: Setting) {
        db.updateData { setting }
    }

}

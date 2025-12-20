package com.khaki.datastore.setting

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.okio.OkioStorage
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.khaki.datastore.setting.model.Setting
import com.khaki.datastore.setting.serializer.SettingJsonSerializer
import kotlinx.coroutines.flow.Flow
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.SYSTEM

internal const val dataStoreFileName = "dice.preferences_pb"

fun createDataStore(producePath: () -> String): DataStore<Preferences> =
    PreferenceDataStoreFactory.createWithPath(
        produceFile = { producePath().toPath() }
    )

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

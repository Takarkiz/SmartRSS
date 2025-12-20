package com.khaki.datastore

import android.app.Application
import com.khaki.datastore.setting.SettingDataStore

actual class DataStoreFactory(
    private val app: Application,
) {
    actual fun createSettingDataStore(): SettingDataStore {
        return SettingDataStore {
            app.filesDir
                .resolve(
                    "setting.json",
                ).absolutePath
        }
    }
}

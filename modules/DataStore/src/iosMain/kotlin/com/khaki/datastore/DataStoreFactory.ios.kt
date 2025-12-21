package com.khaki.datastore

import com.khaki.datastore.setting.SettingDataStore
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

actual class DataStoreFactory {
    actual fun createSettingDataStore(): SettingDataStore {
        return SettingDataStore {
            "${fileDirectory()}/setting.json"
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun fileDirectory(): String {
        val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = true,
            error = null,
        )
        return requireNotNull(documentDirectory?.path) { "Could not find document directory." }
    }
}

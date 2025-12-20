package com.khaki.datastore

import com.khaki.datastore.setting.SettingDataStore

expect class DataStoreFactory {

    fun createSettingDataStore(): SettingDataStore
}

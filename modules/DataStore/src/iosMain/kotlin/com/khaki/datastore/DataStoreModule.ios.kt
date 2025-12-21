package com.khaki.datastore

import com.khaki.datastore.setting.SettingDataStore
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformDataStoreModule(): Module = module {
    single<SettingDataStore> {
        DataStoreFactory().createSettingDataStore()
    }
}

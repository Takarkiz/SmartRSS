package com.khaki.datastore

import android.app.Application
import com.khaki.datastore.setting.SettingDataStore
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformDataStoreModule(): Module = module {
    single<SettingDataStore> {
        DataStoreFactory(get<Application>()).createSettingDataStore()
    }
}

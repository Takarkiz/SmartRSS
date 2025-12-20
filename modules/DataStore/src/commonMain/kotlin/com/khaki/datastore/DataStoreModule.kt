package com.khaki.datastore

import com.khaki.datastore.setting.SettingDataStore
import org.koin.core.module.Module
import org.koin.dsl.module

fun dataStoreModule(
    factory: DataStoreFactory
): Module = module {
    single<SettingDataStore> { factory.createSettingDataStore() }
}

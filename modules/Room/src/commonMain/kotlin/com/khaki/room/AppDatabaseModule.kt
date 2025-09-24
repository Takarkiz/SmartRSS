package com.khaki.room

import com.khaki.room.dao.RssCategoryDao
import org.koin.core.module.Module
import org.koin.dsl.module

fun commonDatabaseModule(): Module = module {
    single<RssCategoryDao> { get<AppDatabase>().rssCategoryDao() }
}

expect fun platformDatabaseModule(): Module

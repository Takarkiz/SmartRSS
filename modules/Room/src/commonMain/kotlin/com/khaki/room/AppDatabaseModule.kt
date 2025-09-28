package com.khaki.room

import com.khaki.room.dao.RssCategoryDao
import com.khaki.room.dao.RssFeedDao
import org.koin.core.module.Module
import org.koin.dsl.module

fun commonDatabaseModule(): Module = module {
    single<RssCategoryDao> { get<AppDatabase>().rssCategoryDao() }

    single<RssFeedDao> { get<AppDatabase>().rssFeedDao() }
}

expect fun platformDatabaseModule(): Module

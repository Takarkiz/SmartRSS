package com.khaki.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.khaki.room.dao.RssCategoryDao
import com.khaki.room.dao.RssFeedDao
import com.khaki.room.entity.ListStringTypeConverter
import com.khaki.room.entity.RSSGroupTypeConverter
import com.khaki.room.entity.RssCategoryEntity
import com.khaki.room.entity.RssFeedEntity

@Database(entities = [RssCategoryEntity::class, RssFeedEntity::class], version = 1)
@TypeConverters(RSSGroupTypeConverter::class, ListStringTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun rssCategoryDao(): RssCategoryDao

    abstract fun rssFeedDao(): RssFeedDao
}

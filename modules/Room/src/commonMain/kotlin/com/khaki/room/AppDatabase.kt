package com.khaki.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.khaki.room.dao.RssCategoryDao
import com.khaki.room.entity.RSSGroupTypeConverter
import com.khaki.room.entity.RssCategoryEntity
import com.khaki.room.entity.RssCategoryOrderTypeConverter

@Database(entities = [RssCategoryEntity::class], version = 1)
@TypeConverters(RSSGroupTypeConverter::class, RssCategoryOrderTypeConverter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun rssCategoryDao(): RssCategoryDao
}

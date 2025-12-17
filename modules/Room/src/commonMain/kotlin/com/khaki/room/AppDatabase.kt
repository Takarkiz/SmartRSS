package com.khaki.room

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.khaki.room.dao.RssCategoryDao
import com.khaki.room.dao.RssFeedDao
import com.khaki.room.entity.ListStringTypeConverter
import com.khaki.room.entity.RSSGroupTypeConverter
import com.khaki.room.entity.RssCategoryEntity
import com.khaki.room.entity.RssCategoryGroupDetailConverter
import com.khaki.room.entity.RssFeedEntity
import com.khaki.room.entity.UserRatingConverter

@Database(
    entities = [RssCategoryEntity::class, RssFeedEntity::class],
    version = 1,
)
@TypeConverters(
    RSSGroupTypeConverter::class,
    ListStringTypeConverter::class,
    RssCategoryGroupDetailConverter::class,
    UserRatingConverter::class,
)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun rssCategoryDao(): RssCategoryDao

    abstract fun rssFeedDao(): RssFeedDao
}

@Suppress("KotlinNoActualForExpect")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}

const val APP_DATABASE_NAME = "app-database.db"

fun getAppDatabase(
    builder: RoomDatabase.Builder<AppDatabase>
): AppDatabase {
    return builder
        .setDriver(BundledSQLiteDriver())
        .build()
}

package com.khaki.room

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

fun provideDatabase(context: Context): RoomDatabase.Builder<AppDatabase> {
    return Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        APP_DATABASE_NAME
    )
}

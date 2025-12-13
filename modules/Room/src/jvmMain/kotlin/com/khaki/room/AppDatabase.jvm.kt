package com.khaki.room

import androidx.room.Room
import androidx.room.RoomDatabase
import java.io.File

fun provideDatabase(): RoomDatabase.Builder<AppDatabase> {
    // 再起動時にも保存されるようにするには~/Library/Application Support/[your-app] フォルダを利用すること
    val dbFile = File(System.getProperty("java.io.tmpdir"), APP_DATABASE_NAME)
    return Room.databaseBuilder<AppDatabase>(
        name = dbFile.absolutePath,
    )
}

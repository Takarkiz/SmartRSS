package com.khaki.room.entity

import androidx.room.TypeConverter

class RSSGroupTypeConverter {

    @TypeConverter
    fun fromRSSGroupType(value: RSSGroupType): String {
        return value.name
    }

    @TypeConverter
    fun toRSSGroupType(value: String): RSSGroupType {
        return RSSGroupType.valueOf(value)
    }
}

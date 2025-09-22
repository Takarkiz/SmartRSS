package com.khaki.room.entity

import androidx.room.TypeConverter

class RssCategoryOrderTypeConverter {

    @TypeConverter
    fun fromRssCategoryOrderType(value: RssCategoryOrderType): String {
        return value.name
    }

    @TypeConverter
    fun toRssCategoryOrderType(value: String): RssCategoryOrderType {
        return RssCategoryOrderType.valueOf(value)
    }
}

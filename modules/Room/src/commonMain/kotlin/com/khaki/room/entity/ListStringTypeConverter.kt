package com.khaki.room.entity

import androidx.room.TypeConverter

/**
 * Simple CSV-based converter for List<String> <-> String
 * Note: This assumes that the individual strings do not contain the delimiter ",".
 * Adjust to JSON if needed in the future.
 */
class ListStringTypeConverter {
    private val delimiter = ","

    @TypeConverter
    fun fromList(list: List<String>?): String {
        if (list.isNullOrEmpty()) return ""
        return list.joinToString(delimiter)
    }

    @TypeConverter
    fun toList(value: String?): List<String> {
        if (value.isNullOrEmpty()) return emptyList()
        return value.split(delimiter).map { it.trim() }.filter { it.isNotEmpty() }
    }
}

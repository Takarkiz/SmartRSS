package com.khaki.room.entity

import androidx.room.TypeConverter

class UserRatingConverter {
    @TypeConverter
    fun fromUserRating(value: UserRating): String {
        return value.name
    }

    @TypeConverter
    fun toUserRating(value: String): UserRating {
        return UserRating.valueOf(value)
    }
}

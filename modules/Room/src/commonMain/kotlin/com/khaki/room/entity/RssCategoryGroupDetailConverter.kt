package com.khaki.room.entity

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

class RssCategoryGroupDetailConverter {

    companion object {

        // Sealed interfaceを扱うための特別なJsonパーサーを準備
        private val json = Json {
            serializersModule = SerializersModule {
                polymorphic(RSSCategoryGroupDetail::class) {
                    subclass(RSSCategoryGroupDetail.Qiita::class)
                    subclass(RSSCategoryGroupDetail.Zenn::class)
                    subclass(RSSCategoryGroupDetail.Hatena::class)
                    subclass(RSSCategoryGroupDetail.Other::class)
                }
            }
        }
    }

    @TypeConverter
    fun fromRssCategoryGroupDetail(detail: RSSCategoryGroupDetail): String {
        // オブジェクトをJSON文字列に変換！
        return json.encodeToString(detail)
    }

    @TypeConverter
    fun toRssCategoryGroupDetail(jsonString: String): RSSCategoryGroupDetail {
        // JSON文字列を元のオブジェクトに復元！
        return json.decodeFromString(jsonString)
    }
}

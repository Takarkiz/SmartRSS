package com.khaki.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RssCategoryEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "category_name") val name: String,
    val description: String?,
    val following: Boolean = true,
    val url: String,
    val type: RSSGroupType,
)

enum class RSSGroupType {
    Qiita,
    Zenn,
    HatenaBlog,
    Github,
    Others;
}

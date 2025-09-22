package com.khaki.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RssCategoryEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "category_name") val name: String,
    @ColumnInfo(name = "rss_feed_id") val rssFeedId: String,
    val following: Boolean = true,
    val url: String,
    val type: RSSGroupType,
    val orderType: RssCategoryOrderType,
)

enum class RSSGroupType {
    Qiita,
    Zenn,
    HatenaBlog,
    Github,
    Others;
}

enum class RssCategoryOrderType {
    UserId,
    Tag,
    Popular,
    URL;
}

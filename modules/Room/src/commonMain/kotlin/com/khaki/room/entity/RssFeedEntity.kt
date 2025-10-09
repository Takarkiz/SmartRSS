package com.khaki.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity
data class RssFeedEntity(
    @PrimaryKey val id: String,
    val title: String,
    @ColumnInfo("feed_url") val feedUrl: String,
    @ColumnInfo("pub_date") val pubDate: String,
    val description: String,
    @ColumnInfo("type") val type: RSSCategoryGroupDetail,
    @ColumnInfo("category_id") val categoryId: String,
    @ColumnInfo("tag_ids") val tagIds: List<String> = emptyList(),
    @ColumnInfo("is_showed") val isShowed: Boolean = false,
    @ColumnInfo("is_read") val isRead: Boolean = false,
    @ColumnInfo("is_favorite") val isFavorite: Boolean = false,
    @ColumnInfo("is_bookmarked") val isBookmarked: Boolean = false,
    @ColumnInfo("recommend_score") val recommendScore: Int = 0,
)

@Serializable
sealed interface RSSCategoryGroupDetail {

    @Serializable
    data class Qiita(
        val authorName: String,
    ) : RSSCategoryGroupDetail

    @Serializable
    data class Zenn(
        val authorName: String,
        val thumbnailUrl: String?
    ) : RSSCategoryGroupDetail

    @Serializable
    data class Hatena(
        val authorName: String,
        val thumbnailUrl: String?,
    ) : RSSCategoryGroupDetail

    @Serializable
    data class Other(
        val thumbnailUrl: String?,
    ) : RSSCategoryGroupDetail
}

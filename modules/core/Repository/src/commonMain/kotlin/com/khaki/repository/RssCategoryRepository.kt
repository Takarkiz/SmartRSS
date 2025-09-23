package com.khaki.repository

import com.khaki.modules.core.model.feed.RssCategory
import kotlinx.coroutines.flow.Flow

interface RssCategoryRepository {

    fun getAllCategories(): Flow<List<RssCategory>>

    fun getAllFollowingCategories(): Flow<List<RssCategory>>

    fun getCategoriesByType(type: RssCategory.RSSGroupType): Flow<List<RssCategory>>

    suspend fun doesUrlExist(url: String): Boolean

    suspend fun insertRssCategory(category: RssCategory)

    suspend fun updateFollowingState(id: String, isFollowing: Boolean): Boolean
}

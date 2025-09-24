package com.khaki.repositoryimpl

import com.khaki.modules.core.model.feed.RssCategory
import com.khaki.modules.core.model.feed.URL
import com.khaki.repository.RssCategoryRepository
import com.khaki.room.dao.RssCategoryDao
import com.khaki.room.entity.RSSGroupType
import com.khaki.room.entity.RssCategoryEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RssCategoryRepositoryImpl(
    private val database: RssCategoryDao
) : RssCategoryRepository {
    override fun getAllCategories(): Flow<List<RssCategory>> {
        return database.getAll()
            .map { categories ->
                categories.map { category ->
                    entityToModel(category)
                }
            }
    }

    override fun getAllFollowingCategories(): Flow<List<RssCategory>> {
        return database.getAllFollowing()
            .map { categories ->
                categories.map { category ->
                    entityToModel(category)
                }
            }
    }

    override fun getCategoriesByType(type: RssCategory.RSSGroupType): Flow<List<RssCategory>> {
        val groupType = when (type) {
            RssCategory.RSSGroupType.Qiita -> RSSGroupType.Qiita
            RssCategory.RSSGroupType.Zenn -> RSSGroupType.Zenn
            RssCategory.RSSGroupType.HatenaBlog -> RSSGroupType.HatenaBlog
            RssCategory.RSSGroupType.Github -> RSSGroupType.Github
            RssCategory.RSSGroupType.Others -> RSSGroupType.Others
        }
        return database.getRssCategoryByType(groupType)
            .map { categories ->
                categories.map { category ->
                    entityToModel(category)
                }
            }
    }

    override suspend fun doesUrlExist(url: String): Boolean {
        return database.doesUrlExist(url)
    }

    override suspend fun insertRssCategory(category: RssCategory) {
        val entity = RssCategoryEntity(
            id = category.id,
            name = category.name,
            description = category.description,
            following = category.following,
            url = category.url,
            type = when (category.type) {
                RssCategory.RSSGroupType.Qiita -> RSSGroupType.Qiita
                RssCategory.RSSGroupType.Zenn -> RSSGroupType.Zenn
                RssCategory.RSSGroupType.HatenaBlog -> RSSGroupType.HatenaBlog
                RssCategory.RSSGroupType.Github -> RSSGroupType.Github
                RssCategory.RSSGroupType.Others -> RSSGroupType.Others
            }
        )
        database.insertRssCategory(entity)
    }

    override suspend fun updateFollowingState(
        id: String,
        isFollowing: Boolean
    ): Boolean {
        return database.updateFollowingState(id, isFollowing) > 0
    }

    private fun entityToModel(categoryEntity: RssCategoryEntity): RssCategory {
        return RssCategory(
            id = categoryEntity.id,
            name = categoryEntity.name,
            description = categoryEntity.description,
            following = categoryEntity.following,
            url = categoryEntity.url,
            type = when (categoryEntity.type) {
                RSSGroupType.Qiita -> RssCategory.RSSGroupType.Qiita
                RSSGroupType.Zenn -> RssCategory.RSSGroupType.Zenn
                RSSGroupType.HatenaBlog -> RssCategory.RSSGroupType.HatenaBlog
                RSSGroupType.Github -> RssCategory.RSSGroupType.Github
                RSSGroupType.Others -> RssCategory.RSSGroupType.Others
            },
            formType = when (categoryEntity.type) {
                RSSGroupType.Qiita -> RssUrlDeserializer.fromQiitaUrl(categoryEntity.url)
                RSSGroupType.Zenn -> RssUrlDeserializer.fromZennUrl(categoryEntity.url)
                RSSGroupType.HatenaBlog -> RssUrlDeserializer.fromHatenaBlogUrl(categoryEntity.url)
                RSSGroupType.Github -> URL.of(categoryEntity.url)
                RSSGroupType.Others -> URL.of(categoryEntity.url)
            }
        )
    }
}

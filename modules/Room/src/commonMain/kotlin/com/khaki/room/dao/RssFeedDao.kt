package com.khaki.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.khaki.room.entity.RssFeedEntity
import com.khaki.room.entity.UserRating
import kotlinx.coroutines.flow.Flow

@Dao
interface RssFeedDao {

    /**
     * 全ての RssFeedEntity を返す
     */
    @Query("SELECT * FROM RssFeedEntity")
    fun getFeeds(): Flow<List<RssFeedEntity>>

    /**
     * 指定されたIDの RssFeedEntity を返す
     */
    @Query("SELECT * FROM RssFeedEntity WHERE id = :id")
    suspend fun getFeedsById(id: String): RssFeedEntity?

    /**
     * 指定されたカテゴリIDに紐づく RssFeedEntity の一覧を返す
     */
    @Query("SELECT * FROM RssFeedEntity WHERE category_id = :categoryId")
    fun getFeedsByCategoryId(categoryId: String): Flow<List<RssFeedEntity>>

    /**
     * 新しい RssFeedEntity を1件登録する
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFeed(feed: RssFeedEntity)

    /**
     * 複数の RssFeedEntity を一括登録する
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFeeds(feeds: List<RssFeedEntity>)

    /**
     * 既存の RssFeedEntity を更新する
     */
    @Update
    suspend fun updateFeed(feed: RssFeedEntity)

    /**
     * 指定されたIDの RssFeedEntity を削除する
     */
    @Query("DELETE FROM RssFeedEntity WHERE id = :id")
    suspend fun deleteFeed(id: String)

    /**
     * 指定されたIDの RssFeedEntity の表示状態を既読リスト表示用に更新する
     */
    @Query("UPDATE RssFeedEntity SET is_showed = 1 WHERE id = :id")
    suspend fun updateShowedState(id: String)

    /**
     * 指定されたIDの RssFeedEntity の既読状態を更新する
     */
    @Query("UPDATE RssFeedEntity SET is_read = 1 WHERE id = :id")
    suspend fun updateReadState(id: String)

    /**
     * 指定されたIDの RssFeedEntity のお気に入り状態を更新する
     * @param id 更新対象のエンティティのID
     * @param isFavorite 新しいお気に入り状態
     */
    @Query("UPDATE RssFeedEntity SET is_favorite = :isFavorite WHERE id = :id")
    suspend fun updateFavoriteState(id: String, isFavorite: Boolean)

    /**
     * 指定されたIDの RssFeedEntity のブックマーク状態を更新する
     * @param id 更新対象のエンティティのID
     * @param isBookmarked 新しいブックマーク状態
     */
    @Query("UPDATE RssFeedEntity SET is_bookmarked = :isBookmarked WHERE id = :id")
    suspend fun updateBookmarkedState(id: String, isBookmarked: Boolean)

    /**
     * 指定されたIDの RssFeedEntity のユーザー評価を更新する
     * @param id 更新対象のエンティティのID
     * @param userRating 新しいユーザー評価
     */
    @Query("UPDATE RssFeedEntity SET user_rating = :userRating WHERE id = :id")
    suspend fun updateUserRating(id: String, userRating: UserRating)

    /**
     * 全ての RssFeedEntity を削除する
     */
    @Query("DELETE FROM RssFeedEntity")
    suspend fun deleteAllFeeds()
}

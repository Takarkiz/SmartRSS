package com.khaki.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.khaki.room.entity.RSSGroupType
import com.khaki.room.entity.RssCategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RssCategoryDao {

    /**
     * 全ての RssCategoryEntity を返す
     */
    @Query("SELECT * FROM rsscategoryentity")
    fun getAll(): Flow<List<RssCategoryEntity>>

    /**
     * ユーザーが現在フォローしている RssCategoryEntity を返す
     */
    @Query("SELECT * FROM rsscategoryentity WHERE `following` = 1")
    fun getAllFollowing(): Flow<List<RssCategoryEntity>>

    /**
     * 特定のタイプのみの RssCategoryEntity を返す
     */
    @Query("SELECT * FROM rsscategoryentity WHERE `type` = :type")
    fun getRssCategoryByType(type: RSSGroupType): Flow<List<RssCategoryEntity>>

    /**
     * 指定されたURLが既にテーブルに存在するかどうかを確認する
     * @param url 確認するURL
     * @return 存在する場合は true、そうでない場合は false
     */
    @Query("SELECT EXISTS(SELECT 1 FROM rsscategoryentity WHERE url = :url)")
    suspend fun doesUrlExist(url: String): Boolean

    /**
     * 新しい RssCategoryEntity を登録する
     */
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertRssCategory(category: RssCategoryEntity)

    /**
     * 指定されたIDのRssCategoryEntityのfollowing状態を更新する
     *
     * @param id 更新対象のエンティティのID
     * @param isFollowing 新しいfollowing状態 (trueまたはfalse)
     * @return 更新に成功した行数 (通常は1か0)
     */
    @Query("UPDATE RssCategoryEntity SET `following` = :isFollowing WHERE id = :id")
    suspend fun updateFollowingState(id: String, isFollowing: Boolean): Int
}

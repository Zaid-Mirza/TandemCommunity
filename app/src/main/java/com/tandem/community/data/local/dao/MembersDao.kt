package com.tandem.community.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tandem.community.data.local.entities.MembersEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MembersDao {

    @Query("SELECT * FROM members ")
    fun getMembersLikes(): Flow<List<MembersEntity>>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertLike(entity: MembersEntity)

    @Query("DELETE FROM members WHERE memberId = :memberId")
    suspend fun removeLike(memberId: Int)
}
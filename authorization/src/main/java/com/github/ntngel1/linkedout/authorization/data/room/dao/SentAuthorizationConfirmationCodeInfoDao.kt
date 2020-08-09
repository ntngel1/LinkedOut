package com.github.ntngel1.linkedout.authorization.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.github.ntngel1.linkedout.authorization.data.room.entity.SentAuthorizationConfirmationCodeInfoRoomEntity

@Dao
interface SentAuthorizationConfirmationCodeInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(info: SentAuthorizationConfirmationCodeInfoRoomEntity)
}
package com.dvm.database.impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dvm.database.api.models.Notification
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao {

    @Query(
        """
            SELECT *
            FROM notification
        """
    )
    fun notifications(): Flow<List<Notification>>

    @Query(
        """
            SELECT COUNT()
            FROM notification
            WHERE seen = 0
        """
    )
    fun count(): Flow<Int>

    @Query(
        """
            UPDATE notification
            SET seen = 1
            WHERE id IN (:id)
        """
    )
    suspend fun setSeen(id: List<Int>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notification: Notification)
}
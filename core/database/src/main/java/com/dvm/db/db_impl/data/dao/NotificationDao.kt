package com.dvm.db.db_impl.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.dvm.db.db_api.data.models.Notification
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
            UPDATE notification
            SET seen = 1
            WHERE id IN (:id)
        """
    )
    suspend fun setSeen(id: List<Int>)

    @Insert
    suspend fun insertNotification(notification: Notification)
}
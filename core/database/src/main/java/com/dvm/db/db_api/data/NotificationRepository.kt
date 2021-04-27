package com.dvm.db.db_api.data

import com.dvm.db.db_api.data.models.Notification
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    fun notifications(): Flow<List<Notification>>
    suspend fun setSeen(id: List<Int>)
    suspend fun insertNotification(notification: Notification)
}
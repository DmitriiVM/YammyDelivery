package com.dvm.db.api

import com.dvm.db.api.models.Notification
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    fun notifications(): Flow<List<Notification>>
    suspend fun setSeen(id: List<Int>)
    suspend fun insertNotification(notification: Notification)
}
package com.dvm.database.api

import com.dvm.database.Notification
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    fun notifications(): Flow<List<Notification>>
    fun count() : Flow<Int>
    suspend fun setSeen(id: List<Int>)
    suspend fun insertNotification(
        title: String,
        text: String,
        seen: Boolean?
    )
}
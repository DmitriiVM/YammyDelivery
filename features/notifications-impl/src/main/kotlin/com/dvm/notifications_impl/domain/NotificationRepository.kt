package com.dvm.notifications_impl.domain

import com.dvm.notifications_api.domain.model.Notification
import kotlinx.coroutines.flow.Flow

internal interface NotificationRepository {
    fun notifications(): Flow<List<Notification>>
    fun count(): Flow<Int>
    suspend fun setSeen(id: List<Int>)
    suspend fun insertNotification(
        title: String,
        text: String,
        seen: Boolean?
    )
}
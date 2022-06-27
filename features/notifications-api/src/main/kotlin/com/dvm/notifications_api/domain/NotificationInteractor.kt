package com.dvm.notifications_api.domain

import com.dvm.notifications_api.domain.model.Notification
import kotlinx.coroutines.flow.Flow

interface NotificationInteractor {
    suspend fun setSeen(
        notifications: List<Notification>,
        lastItemPosition: Int
    )

    fun notifications(): Flow<List<Notification>>
    fun count(): Flow<Int>
    suspend fun insertNotification(
        title: String,
        text: String,
        seen: Boolean?
    )
}
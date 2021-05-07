package com.dvm.db.impl.data

import com.dvm.db.api.NotificationRepository
import com.dvm.db.api.models.Notification
import com.dvm.db.impl.data.dao.NotificationDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class DefaultNotificationRepository @Inject constructor(
    private val notificationDao: NotificationDao
) : NotificationRepository {

    override fun notifications(): Flow<List<Notification>> = notificationDao.notifications()

    override suspend fun setSeen(id: List<Int>) {
        notificationDao.setSeen(id)
    }

    override suspend fun insertNotification(notification: Notification) {
        notificationDao.insertNotification(notification)
    }
}
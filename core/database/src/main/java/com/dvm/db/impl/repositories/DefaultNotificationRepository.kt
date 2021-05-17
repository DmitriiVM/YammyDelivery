package com.dvm.db.impl.repositories

import com.dvm.db.api.NotificationRepository
import com.dvm.db.api.models.Notification
import com.dvm.db.impl.dao.NotificationDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class DefaultNotificationRepository @Inject constructor(
    private val notificationDao: NotificationDao
) : NotificationRepository {

    override fun notifications(): Flow<List<Notification>> = notificationDao.notifications()

    override fun count(): Flow<Int> = notificationDao.count()

    override suspend fun setSeen(id: List<Int>) =
        withContext(Dispatchers.IO) {
            notificationDao.setSeen(id)
        }

    override suspend fun insertNotification(notification: Notification) =
        withContext(Dispatchers.IO) {
            notificationDao.insertNotification(notification)
        }
}
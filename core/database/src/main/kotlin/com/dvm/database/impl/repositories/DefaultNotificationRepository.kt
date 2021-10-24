package com.dvm.database.impl.repositories

import com.dvm.database.api.NotificationRepository
import com.dvm.database.api.models.Notification
import com.dvm.database.impl.dao.NotificationDao
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

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
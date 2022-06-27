package com.dvm.notifications_impl.data.database

import com.dvm.notifications_api.domain.model.Notification
import com.dvm.notifications_impl.data.mappers.toNotification
import com.dvm.notifications_impl.domain.NotificationRepository
import com.dvm.notificationsdatabase.NotificationQueries
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOne
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

internal class DefaultNotificationRepository(
    private val notificationQueries: NotificationQueries
) : NotificationRepository {

    override fun notifications(): Flow<List<Notification>> =
        notificationQueries
            .notifications()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { notifications ->
                notifications.map { it.toNotification() }
            }

    override fun count(): Flow<Int> =
        notificationQueries
            .count()
            .asFlow()
            .mapToOne(Dispatchers.IO)
            .map { it.toInt() }

    override suspend fun setSeen(id: List<Int>) =
        withContext(Dispatchers.IO) {
            id.forEach {
                notificationQueries.setSeen(it.toLong())
            }
        }

    override suspend fun insertNotification(
        title: String,
        text: String,
        seen: Boolean?
    ) =
        withContext(Dispatchers.IO) {
            notificationQueries.insert(
                title = title,
                text = text,
                seen = seen,
            )
        }
}
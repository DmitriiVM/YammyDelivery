package com.dvm.notifications_impl.domain

import com.dvm.notifications_api.domain.NotificationInteractor
import com.dvm.notifications_api.domain.model.Notification
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow

internal class DefaultNotificationInteractor(
    private val repository: NotificationRepository
) : NotificationInteractor {

    private var previousLastItemPosition = 0

    override suspend fun setSeen(
        notifications: List<Notification>,
        lastItemPosition: Int
    ) {
        delay(1000)
        repository.setSeen(
            notifications
                .subList(
                    fromIndex = previousLastItemPosition,
                    toIndex = (lastItemPosition + 1)
                        .coerceAtLeast(previousLastItemPosition)
                )
                .map { it.id.toInt() }
        )
        previousLastItemPosition = lastItemPosition
    }

    override fun notifications(): Flow<List<Notification>> =
        repository.notifications()

    override fun count(): Flow<Int> =
        repository.count()

    override suspend fun insertNotification(
        title: String,
        text: String,
        seen: Boolean?
    ) {
        repository.insertNotification(
            title = title,
            text = text,
            seen = seen
        )
    }
}
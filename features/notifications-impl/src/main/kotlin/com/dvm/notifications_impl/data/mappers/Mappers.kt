package com.dvm.notifications_impl.data.mappers

import com.dvm.notifications_api.domain.model.Notification
import com.dvm.notificationsdatabase.NotificationEntity

fun NotificationEntity.toNotification() =
    Notification(
        id = id,
        title = title,
        text = text,
        seen = seen,
    )
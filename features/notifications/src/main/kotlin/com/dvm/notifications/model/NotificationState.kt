package com.dvm.notifications.model

import com.dvm.database.api.models.Notification

data class NotificationState(
    val notifications: List<Notification> = emptyList()
)
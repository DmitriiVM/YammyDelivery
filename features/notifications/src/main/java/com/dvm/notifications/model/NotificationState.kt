package com.dvm.notifications.model

import com.dvm.db.api.models.Notification

data class NotificationState(
    val notifications: List<Notification> = emptyList()
)
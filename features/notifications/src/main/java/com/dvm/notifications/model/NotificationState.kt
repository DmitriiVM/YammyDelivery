package com.dvm.notifications.model

import androidx.compose.runtime.Immutable
import com.dvm.database.Notification

@Immutable
data class NotificationState(
    val notifications: List<Notification> = emptyList()
)
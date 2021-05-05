package com.dvm.notifications.model

import androidx.compose.runtime.Immutable
import com.dvm.db.api.data.models.Notification

@Immutable
data class NotificationState(
    val notifications: List<Notification> = emptyList()
)
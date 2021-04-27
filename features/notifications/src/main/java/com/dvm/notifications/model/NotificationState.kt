package com.dvm.notifications.model

import androidx.compose.runtime.Immutable
import com.dvm.db.db_api.data.models.Notification

@Immutable
data class NotificationState(
    val notifications: List<Notification> = emptyList()
)
package com.dvm.notifications.model

import androidx.compose.runtime.Immutable
import com.dvm.database.api.models.Notification

@Immutable
internal data class NotificationState(
    val notifications: List<Notification> = emptyList()
)
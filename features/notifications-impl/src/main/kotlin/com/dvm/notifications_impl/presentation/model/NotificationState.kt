package com.dvm.notifications_impl.presentation.model

import androidx.compose.runtime.Immutable
import com.dvm.notifications_api.domain.model.Notification

@Immutable
internal data class NotificationState(
    val notifications: List<Notification> = emptyList()
)
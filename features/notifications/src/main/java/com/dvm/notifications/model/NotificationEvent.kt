package com.dvm.notifications.model

sealed class NotificationEvent {
    class VisibleItemChange(val lastItemPosition: Int) : NotificationEvent()
    object BackClick: NotificationEvent()
}
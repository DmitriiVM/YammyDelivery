package com.dvm.notifications.model

sealed class NotificationEvent {
    class ChangeVisibleItem(val lastItemPosition: Int) : NotificationEvent()
    object Back: NotificationEvent()
}
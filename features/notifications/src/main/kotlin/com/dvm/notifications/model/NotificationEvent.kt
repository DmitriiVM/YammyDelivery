package com.dvm.notifications.model

internal sealed class NotificationEvent {
    class ChangeVisibleItem(val lastItemPosition: Int) : NotificationEvent()
    object Back : NotificationEvent()
}
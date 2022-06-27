package com.dvm.notifications_impl.presentation.model

internal sealed class NotificationEvent {
    class ChangeVisibleItem(val lastItemPosition: Int) : NotificationEvent()
    object Back : NotificationEvent()
}
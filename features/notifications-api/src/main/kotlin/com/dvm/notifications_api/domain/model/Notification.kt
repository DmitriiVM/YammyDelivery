package com.dvm.notifications_api.domain.model

data class Notification(
    val id: Long,
    val title: String,
    val text: String,
    val seen: Boolean?
)
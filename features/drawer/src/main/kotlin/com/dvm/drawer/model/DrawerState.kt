package com.dvm.drawer.model

import androidx.compose.runtime.Immutable

@Immutable
internal data class DrawerState(
    val name: String = "",
    val email: String = "",
    val cartQuantity: Int = 0,
    val newNotificationCount: Int = 0,
    val alert: Int? = null,
)
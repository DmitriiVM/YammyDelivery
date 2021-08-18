package com.dvm.appmenu.model

import androidx.compose.runtime.Immutable

@Immutable
internal data class AppMenuState(
    val name: String = "",
    val email: String = "",
    val cartQuantity: Int = 0,
    val newNotificationCount: Int = 0,
    val alert: String? = null,
)
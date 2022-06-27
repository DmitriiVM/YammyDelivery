package com.dvm.drawer_impl.presentation.model

import com.dvm.utils.DrawerItem

internal sealed class DrawerEvent {
    data class SelectItem(val item: DrawerItem) : DrawerEvent()
    object Auth : DrawerEvent()
    object Logout : DrawerEvent()
    object DismissAlert : DrawerEvent()
}
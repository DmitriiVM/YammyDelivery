package com.dvm.appmenu.model

import com.dvm.utils.DrawerItem

internal sealed class AppMenuEvent{
    data class SelectItem(val item: DrawerItem) : AppMenuEvent()
    object Auth : AppMenuEvent()
    object Logout : AppMenuEvent()
    object DismissAlert : AppMenuEvent()
}
package com.dvm.appmenu.model

import com.dvm.utils.DrawerItem

internal sealed class AppMenuEvent{
    data class ItemClick(val drawerItem: DrawerItem) : AppMenuEvent()
    object AuthClick : AppMenuEvent()
    object LogoutClick : AppMenuEvent()
    object DismissAlert : AppMenuEvent()
}
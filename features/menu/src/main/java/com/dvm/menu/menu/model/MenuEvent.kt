package com.dvm.menu.menu.model

internal sealed class MenuEvent{
    data class MenuItemClick(val id: String): MenuEvent()
    object SearchClick: MenuEvent()
}
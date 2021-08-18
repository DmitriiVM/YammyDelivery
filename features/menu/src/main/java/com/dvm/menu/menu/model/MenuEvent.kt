package com.dvm.menu.menu.model

internal sealed class MenuEvent{
    data class OpenMenuItem(val id: String): MenuEvent()
    object Search: MenuEvent()
}
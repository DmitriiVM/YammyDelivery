package com.dvm.menu_impl.presentation.menu.model

internal sealed class MenuEvent {
    data class OpenMenuItem(val id: String) : MenuEvent()
    object Search : MenuEvent()
}
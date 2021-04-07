package com.dvm.menu.menu.presentation.model

internal sealed class MenuNavigationEvent {
    object OpenAppMenu: MenuNavigationEvent()
    object NavigateToSearch: MenuNavigationEvent()
    data class NavigateToCategory(val id: String): MenuNavigationEvent()
}
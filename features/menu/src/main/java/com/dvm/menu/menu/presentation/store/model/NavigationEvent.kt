package com.dvm.menu.menu.presentation.store.model

sealed class MenuNavigationEvent {
    object OpenAppMenu: MenuNavigationEvent()
    object NavigateToSearch: MenuNavigationEvent()
    data class NavigateToMenuItem(val id: String): MenuNavigationEvent()
}
package com.dvm.menu.menu.presentation.store.model

sealed class MenuAction{
    object LoadMenu: MenuAction()
    data class NavigateToMenuItem(val id: String):  MenuAction()
    object NavigateToSearch: MenuAction()
    object OpenAppMenu: MenuAction()
}
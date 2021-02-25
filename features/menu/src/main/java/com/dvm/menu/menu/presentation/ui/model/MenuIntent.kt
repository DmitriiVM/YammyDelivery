package com.dvm.menu.menu.presentation.ui.model

sealed class MenuIntent{
    data class MenuItemClick(val id: String): MenuIntent()
    object SearchClick: MenuIntent()
    object AppMenuClick: MenuIntent()
}
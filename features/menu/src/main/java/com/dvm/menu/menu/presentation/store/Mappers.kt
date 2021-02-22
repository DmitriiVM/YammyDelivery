package com.dvm.menu.menu.presentation.store

import com.dvm.menu.menu.presentation.store.model.MenuAction
import com.dvm.menu.menu.presentation.ui.model.MenuIntent

internal fun MenuIntent.toAction(): MenuAction =
    when (this){
        is MenuIntent.MenuItemClick -> MenuAction.NavigateToMenuItem(title)
        MenuIntent.SearchClick -> MenuAction.NavigateToSearch
        MenuIntent.AppMenuClick -> MenuAction.OpenAppMenu
    }
package com.dvm.menu.menu.presentation.ui.model

import com.dvm.menu.menu.domain.model.MenuItem

sealed class MenuState {
    object Loading : MenuState()
    data class Data(val items: List<MenuItem>) : MenuState()
}
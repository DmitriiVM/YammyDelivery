package com.dvm.menu.menu.presentation.store.model

import com.dvm.menu.menu.domain.model.MenuItem

sealed class MenuResult {
    object Loading: MenuResult()
    data class Data(val items : List<MenuItem>): MenuResult()
    data class Error(val exception: Exception): MenuResult()
}
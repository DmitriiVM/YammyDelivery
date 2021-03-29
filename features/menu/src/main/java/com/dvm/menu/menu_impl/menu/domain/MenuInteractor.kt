package com.dvm.menu.menu_impl.menu.domain

import com.dvm.menu.menu_impl.menu.domain.model.MenuItem

internal interface MenuInteractor {
    suspend fun getParentCategories(): List<MenuItem>
}
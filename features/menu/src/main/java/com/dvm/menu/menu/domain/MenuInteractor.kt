package com.dvm.menu.menu.domain

import com.dvm.menu.menu.domain.model.MenuItem

internal interface MenuInteractor {
    suspend fun getParentCategories(): List<MenuItem>
}
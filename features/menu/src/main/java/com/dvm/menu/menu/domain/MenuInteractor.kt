package com.dvm.menu.menu.domain

import com.dvm.menu.menu.domain.model.MenuItem
import com.dvm.menu.menu.repository.MenuRepository
import javax.inject.Inject

class MenuInteractor @Inject constructor(
    private val repository: MenuRepository
) {

    suspend fun getParentCategories(): List<MenuItem> {

        val hasSpecialOffers = repository.hasSpecialOffers()

        return mutableListOf<MenuItem>().apply {
            if (hasSpecialOffers) add(MenuItem.SpecialOffer)
            addAll(
                repository.getMenuItems()
            )
        }
    }
}
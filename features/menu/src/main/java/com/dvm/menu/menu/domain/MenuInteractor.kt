package com.dvm.menu.menu.domain

import com.dvm.menu.menu.domain.model.MenuItem
import com.dvm.menu.menu.repository.CategoryRepository

class MenuInteractor(
    private val repository: CategoryRepository = CategoryRepository()
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
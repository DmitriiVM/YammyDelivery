package com.dvm.menu.menu.domain

import com.dvm.db.db_api.data.CategoryRepository
import com.dvm.db.db_api.data.DishRepository
import com.dvm.menu.menu.domain.model.MenuItem
import javax.inject.Inject

class MenuInteractor @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val dishRepository: DishRepository
) {

    suspend fun getParentCategories(): List<MenuItem> {
        val hasSpecialOffers = dishRepository.hasSpecialOffers()
        val menuItems = categoryRepository
            .getParentCategories()
            .map { category ->
                MenuItem.Item(
                    id = category.id,
                    title = category.name,
                    imageUrl = category.icon
                )
            }
        return mutableListOf<MenuItem>().apply {
            if (hasSpecialOffers) add(MenuItem.SpecialOffer)
            addAll(menuItems)
        }
    }
}
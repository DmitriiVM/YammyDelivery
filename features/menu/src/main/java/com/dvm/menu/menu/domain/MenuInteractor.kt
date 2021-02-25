package com.dvm.menu.menu.domain

import android.content.Context
import com.dvm.db.entities.CategoryDao
import com.dvm.db.entities.DishDao
import com.dvm.menu.menu.domain.model.MenuItem

class MenuInteractor(
    private val categoryDao: CategoryDao,
    private val dishDao: DishDao
) {

    suspend fun getParentCategories(context: Context): List<MenuItem> {

        val hasSpecialOffers = dishDao.hasSpecialOffers()

        return mutableListOf<MenuItem>().apply {
            if (hasSpecialOffers) add(MenuItem.SpecialOffer)
            addAll(
                categoryDao
                    .getParentCategories()
                    .map { category ->
                        MenuItem.Item(
                            id = category.id,
                            title = category.name,
                            imageUrl = category.icon
                        )
                    }  // TODO mapping in data layer
            )
        }
    }
}
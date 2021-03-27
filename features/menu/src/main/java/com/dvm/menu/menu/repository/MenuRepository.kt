package com.dvm.menu.menu.repository

import com.dvm.db.dao.CategoryDao
import com.dvm.db.dao.DishDao
import com.dvm.menu.menu.domain.model.MenuItem
import javax.inject.Inject

class MenuRepository @Inject constructor(
    private val categoryDao: CategoryDao,
    private val dishDao: DishDao,
) {

    suspend fun hasSpecialOffers() = dishDao.hasSpecialOffers()

    suspend fun getMenuItems() =
        categoryDao
            .getParentCategories()
            .map { category ->
                MenuItem.Item(
                    id = category.id,
                    title = category.name,
                    imageUrl = category.icon
                )
            }
}
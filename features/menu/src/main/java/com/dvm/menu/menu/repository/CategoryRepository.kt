package com.dvm.menu.menu.repository

import com.dvm.db.dao.CategoryDao
import com.dvm.db.dao.DishDao
import com.dvm.menu.Graph
import com.dvm.menu.menu.domain.model.MenuItem

class CategoryRepository(
    private val categoryDao: CategoryDao = Graph.categoryDao,
    private val dishDao: DishDao = Graph.dishDao
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
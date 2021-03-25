package com.dvm.menu.menu.repository

import com.dvm.db.dao.CategoryDao
import com.dvm.db.dao.DishDao
import com.dvm.db.temp.DbGraph
import com.dvm.menu.menu.domain.model.MenuItem

class CategoryRepository(
    private val categoryDao: CategoryDao = DbGraph.categoryDao,
    private val dishDao: DishDao = DbGraph.dishDao
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
package com.dvm.menu.category.domain

import com.dvm.db.entities.CategoryDao
import com.dvm.db.entities.DishDao
import com.dvm.menu.Graph

class CategoryInteractor(
    private val categoryDao: CategoryDao = Graph.categoryDao,
    private val dishDao: DishDao = Graph.dishDao
) {

    suspend fun getSubcategories(categoryId: String) =
        categoryDao.getChildCategories(categoryId)

    suspend fun getDishes(categoryId: String) =
        dishDao.getDishes(categoryId)

}
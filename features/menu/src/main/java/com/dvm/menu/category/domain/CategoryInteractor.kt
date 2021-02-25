package com.dvm.menu.category.domain

import com.dvm.db.entities.CategoryDao
import com.dvm.db.entities.DishDao

class CategoryInteractor(
    private val categoryDao: CategoryDao,
    private val dishDao: DishDao
) {


    suspend fun getSubcategories(categoryId: String) =
        categoryDao.getChildCategories(categoryId)

    suspend fun getDishes(categoryId: String) =
        dishDao.getDishes(categoryId)

}
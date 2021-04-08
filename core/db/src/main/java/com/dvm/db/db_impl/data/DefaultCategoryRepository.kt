package com.dvm.db.db_impl.data

import com.dvm.db.db_api.data.CategoryRepository
import com.dvm.db.db_api.data.models.Category
import com.dvm.db.db_api.data.models.ParentCategory
import com.dvm.db.db_api.data.models.Subcategory
import com.dvm.db.db_impl.data.dao.CategoryDao
import javax.inject.Inject

internal class DefaultCategoryRepository @Inject constructor(
    private val categoryDao: CategoryDao
) : CategoryRepository{
    override suspend fun getParentCategories(): List<ParentCategory>  =
        categoryDao.getParentCategories()

    override suspend fun getChildCategories(id: String): List<Subcategory>  =
        categoryDao.getChildCategories(id)

    override suspend fun getCategoryTitle(categoryId: String): String  =
        categoryDao.getCategoryTitle(categoryId)

    override suspend fun insertCategories(categories: List<Category>)  =
        categoryDao.insertCategories(categories)
}
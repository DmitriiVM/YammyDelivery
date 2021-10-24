package com.dvm.database.impl.repositories

import com.dvm.database.api.CategoryRepository
import com.dvm.database.api.models.Category
import com.dvm.database.api.models.ParentCategory
import com.dvm.database.api.models.Subcategory
import com.dvm.database.impl.dao.CategoryDao
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

internal class DefaultCategoryRepository @Inject constructor(
    private val categoryDao: CategoryDao
) : CategoryRepository {

    override fun searchParentCategory(query: String): Flow<List<ParentCategory>> =
        categoryDao.searchParentCategory(query)

    override fun searchSubcategory(query: String): Flow<List<Subcategory>> =
        categoryDao.searchSubcategory(query)

    override suspend fun getParentCategories(): List<ParentCategory> =
        withContext(Dispatchers.IO) {
            categoryDao.getParentCategories()
        }

    override suspend fun getSubcategories(parentId: String): List<Subcategory> =
        withContext(Dispatchers.IO) {
            categoryDao.getSubcategories(parentId)
        }

    override suspend fun getCategoryTitle(categoryId: String): String =
        withContext(Dispatchers.IO) {
            categoryDao.getTitle(categoryId)
        }

    override suspend fun insertCategories(categories: List<Category>) =
        withContext(Dispatchers.IO) {
            categoryDao.insertCategories(categories)
        }
}
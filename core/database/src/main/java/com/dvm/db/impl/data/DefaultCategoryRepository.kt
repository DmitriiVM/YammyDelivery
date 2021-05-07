package com.dvm.db.impl.data

import com.dvm.db.api.CategoryRepository
import com.dvm.db.api.models.Category
import com.dvm.db.api.models.Hint
import com.dvm.db.api.models.ParentCategory
import com.dvm.db.api.models.Subcategory
import com.dvm.db.impl.data.dao.CategoryDao
import com.dvm.db.impl.data.dao.HintDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class DefaultCategoryRepository @Inject constructor(
    private val categoryDao: CategoryDao,
    private val hintDao: HintDao,
) : CategoryRepository {

    override suspend fun getParentCategories(): List<ParentCategory> = withContext(Dispatchers.IO) {
        categoryDao.getParentCategories()
    }

    override suspend fun getSubcategories(id: String): List<Subcategory> =
        withContext(Dispatchers.IO) {
            categoryDao.getSubcategories(id)
        }

    override suspend fun getCategoryTitle(categoryId: String): String =
        withContext(Dispatchers.IO) {
            categoryDao.getCategoryTitle(categoryId)
        }

    override fun searchParentCategory(query: String): Flow<List<ParentCategory>> =
        categoryDao.searchParentCategory(query)

    override fun searchSubcategory(query: String): Flow<List<Subcategory>> =
        categoryDao.searchSubcategory(query)

    override suspend fun insertCategories(categories: List<Category>) =
        withContext(Dispatchers.IO) {
            categoryDao.insertCategories(categories)
        }

    override fun hints(): Flow<List<String>> = hintDao.hints()

    override suspend fun saveHint(query: String) = withContext(Dispatchers.IO) {
        if (hintDao.hintCount() >= 5) {
            hintDao.deleteOldest()
        }
        hintDao.insert(Hint(query, System.currentTimeMillis()))
    }

    override suspend fun removeHint(query: String) = withContext(Dispatchers.IO) {
        hintDao.delete(query)
    }
}
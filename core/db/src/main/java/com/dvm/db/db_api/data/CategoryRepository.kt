package com.dvm.db.db_api.data

import com.dvm.db.db_api.data.models.Category
import com.dvm.db.db_api.data.models.ParentCategory
import com.dvm.db.db_api.data.models.Subcategory
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun getParentCategories(): List<ParentCategory>
    suspend fun getChildCategories(id: String): List<Subcategory>
    suspend fun getCategoryTitle(categoryId: String): String
    suspend fun insertCategories(categories: List<Category>)
    fun hints(): Flow<List<String>>
    suspend fun saveHint(query: String)
    suspend fun removeHint(query: String)
}
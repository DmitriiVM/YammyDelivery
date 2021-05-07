package com.dvm.db.api

import com.dvm.db.api.models.Category
import com.dvm.db.api.models.ParentCategory
import com.dvm.db.api.models.Subcategory
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun getParentCategories(): List<ParentCategory>
    suspend fun getSubcategories(id: String): List<Subcategory>
    suspend fun getCategoryTitle(categoryId: String): String
    fun searchParentCategory(query: String): Flow<List<ParentCategory>>
    fun searchSubcategory(query: String): Flow<List<Subcategory>>
    suspend fun insertCategories(categories: List<Category>)
    fun hints(): Flow<List<String>>
    suspend fun saveHint(query: String)
    suspend fun removeHint(query: String)
}
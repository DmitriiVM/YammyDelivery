package com.dvm.menu_impl.domain.repository

import com.dvm.menu_api.domain.model.Category
import com.dvm.menu_api.domain.model.ParentCategory
import com.dvm.menu_api.domain.model.Subcategory
import kotlinx.coroutines.flow.Flow

internal interface CategoryRepository {
    fun searchParentCategory(query: String): Flow<List<ParentCategory>>
    fun searchSubcategory(query: String): Flow<List<Subcategory>>
    suspend fun getParentCategories(): List<ParentCategory>
    suspend fun getSubcategories(parentId: String): List<Subcategory>
    suspend fun getCategoryTitle(categoryId: String): String
    suspend fun insertCategories(categories: List<Category>)
}
package com.dvm.menu_api.domain

import com.dvm.menu_api.domain.model.CardDish
import com.dvm.menu_api.domain.model.Category
import com.dvm.menu_api.domain.model.OrderType
import com.dvm.menu_api.domain.model.ParentCategory
import com.dvm.menu_api.domain.model.Subcategory
import kotlinx.coroutines.flow.Flow

interface CategoryInteractor {
    fun searchParentCategory(query: String): Flow<List<ParentCategory>>
    fun searchSubcategory(query: String): Flow<List<Subcategory>>
    suspend fun getParentCategories(): List<ParentCategory>
    suspend fun getSubcategories(parentId: String): List<Subcategory>
    suspend fun getCategoryTitle(categoryId: String): String
    suspend fun insertCategories(categories: List<Category>)
    suspend fun updateCategories(lastUpdateTime: Long)
    suspend fun order(dishes: List<CardDish>, orderType: OrderType): List<CardDish>

    suspend fun getCategories(
        lastUpdateTime: Long?
    ): List<Category>
}
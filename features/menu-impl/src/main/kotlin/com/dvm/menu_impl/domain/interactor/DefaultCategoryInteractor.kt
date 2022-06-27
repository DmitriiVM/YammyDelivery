package com.dvm.menu_impl.domain.interactor

import com.dvm.menu_api.domain.CategoryInteractor
import com.dvm.menu_api.domain.model.CardDish
import com.dvm.menu_api.domain.model.Category
import com.dvm.menu_api.domain.model.OrderType
import com.dvm.menu_api.domain.model.ParentCategory
import com.dvm.menu_api.domain.model.Subcategory
import com.dvm.menu_impl.domain.api.MenuApi
import com.dvm.menu_impl.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow

internal class DefaultCategoryInteractor(
    private val categoryRepository: CategoryRepository,
    private val menuApi: MenuApi
) : CategoryInteractor {

    override fun searchParentCategory(query: String): Flow<List<ParentCategory>> =
        categoryRepository.searchParentCategory(query)

    override suspend fun updateCategories(lastUpdateTime: Long) {
        val categories = menuApi.getCategories(lastUpdateTime)
        categoryRepository.insertCategories(categories)
    }

    override suspend fun order(dishes: List<CardDish>, orderType: OrderType): List<CardDish> =
        when (orderType) {
            OrderType.ALPHABET_ASC -> dishes.sortedBy { it.name }
            OrderType.ALPHABET_DESC -> dishes.sortedByDescending { it.name }
            OrderType.POPULARITY_ASC -> dishes.sortedBy { it.likes }
            OrderType.POPULARITY_DESC -> dishes.sortedByDescending { it.likes }
            OrderType.RATING_ASC -> dishes.sortedBy { it.rating }
            OrderType.RATING_DESC -> dishes.sortedByDescending { it.rating }
        }

    override fun searchSubcategory(query: String): Flow<List<Subcategory>> =
        categoryRepository.searchSubcategory(query)

    override suspend fun getParentCategories(): List<ParentCategory> =
        categoryRepository.getParentCategories()

    override suspend fun getSubcategories(parentId: String): List<Subcategory> =
        categoryRepository.getSubcategories(parentId)

    override suspend fun getCategoryTitle(categoryId: String): String =
        categoryRepository.getCategoryTitle(categoryId)

    override suspend fun insertCategories(categories: List<Category>) {
        categoryRepository.insertCategories(categories)
    }

    override suspend fun getCategories(lastUpdateTime: Long?): List<Category> =
        menuApi.getCategories(lastUpdateTime)
}
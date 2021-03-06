package com.dvm.menu.category.temp

import com.dvm.db.entities.Dish
import com.dvm.db.entities.Subcategory

sealed class CategoryState {
    object Loading: CategoryState()  // TODO
    data class Error(val error: String): CategoryState()
    data class Data(
        val subcategories: List<Subcategory> = emptyList(),
        val selectedCategory: Subcategory? = null,
        val dishes: List<Dish> = emptyList(),
        val selectedSort: SortType = SortType.ALPHABET_DESC,
        val message: String? = null,
        val showSort: Boolean = false
    ): CategoryState()
}
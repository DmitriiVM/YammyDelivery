package com.dvm.menu.category.temp

import com.dvm.db.entities.Dish
import com.dvm.db.entities.Subcategory

sealed class CategoryState {
    object Loading : CategoryState()  // TODO
    data class Error(val error: String) : CategoryState()
    data class Data(
        val title: String = "",
        val subcategories: List<Subcategory> = emptyList(),
        val selectedCategoryId: String? = null,
        val dishes: List<Dish> = emptyList(),
        val selectedSort: SortType = SortType.ALPHABET_ASC,
        val message: String? = null
    ) : CategoryState()
}
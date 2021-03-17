package com.dvm.menu.category.presentation.model

import androidx.compose.runtime.Immutable
import com.dvm.db.entities.Dish
import com.dvm.db.entities.Subcategory

@Immutable
data class CategoryState(
    val title: String = "",
    val subcategories: List<Subcategory> = emptyList(),
    val selectedCategoryId: String? = null,
    val dishes: List<Dish> = emptyList(),
    val selectedSort: SortType = SortType.ALPHABET_ASC
)
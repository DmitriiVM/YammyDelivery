package com.dvm.menu.category.presentation.model

import androidx.compose.runtime.Immutable
import com.dvm.db.db_api.data.models.Dish
import com.dvm.db.db_api.data.models.Subcategory

@Immutable
internal data class CategoryState(
    val title: String = "",
    val subcategories: List<Subcategory> = emptyList(),
    val selectedCategoryId: String? = null,
    val dishes: List<Dish> = emptyList(),
    val selectedOrder: OrderType = OrderType.ALPHABET_ASC
)
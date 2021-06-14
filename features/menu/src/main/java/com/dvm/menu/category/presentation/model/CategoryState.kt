package com.dvm.menu.category.presentation.model

import androidx.compose.runtime.Immutable
import com.dvm.db.api.models.CategoryDish
import com.dvm.db.api.models.Subcategory

@Immutable
internal data class CategoryState(
    val title: String = "",
    val subcategories: List<Subcategory> = emptyList(),
    val selectedId: String? = null,
    val dishes: List<CategoryDish> = emptyList(),
    val orderType: OrderType = OrderType.ALPHABET_ASC,
    val alertMessage: String? = null
)
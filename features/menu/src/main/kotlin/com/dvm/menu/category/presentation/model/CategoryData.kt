package com.dvm.menu.category.presentation.model

import com.dvm.database.Subcategory
import com.dvm.database.api.models.CardDish
import com.dvm.utils.Text

internal data class CategoryData(
    val title: Text,
    val categoryId: String,
    val orderType: OrderType,
    val subcategories: List<Subcategory>,
    val selectedId: String? = null,
    val dishes: List<CardDish> = emptyList()
)
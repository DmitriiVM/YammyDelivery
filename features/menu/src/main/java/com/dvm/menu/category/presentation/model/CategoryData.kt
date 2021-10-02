package com.dvm.menu.category.presentation.model

import com.dvm.database.api.models.CardDishDetails
import com.dvm.database.api.models.Subcategory

internal data class CategoryData(
    val title: Title,
    val categoryId: String,
    val orderType: OrderType,
    val subcategories: List<Subcategory>,
    val selectedId: String? = null,
    val dishes: List<CardDishDetails> = emptyList()
)
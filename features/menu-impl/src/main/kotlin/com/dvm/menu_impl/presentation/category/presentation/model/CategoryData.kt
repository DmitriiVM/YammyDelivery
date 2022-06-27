package com.dvm.menu_impl.presentation.category.presentation.model

import com.dvm.menu_api.domain.model.CardDish
import com.dvm.menu_api.domain.model.OrderType
import com.dvm.menu_api.domain.model.Subcategory
import com.dvm.utils.Text

internal data class CategoryData(
    val title: Text,
    val categoryId: String,
    val orderType: OrderType,
    val subcategories: List<Subcategory>,
    val selectedId: String? = null,
    val dishes: List<CardDish> = emptyList()
)
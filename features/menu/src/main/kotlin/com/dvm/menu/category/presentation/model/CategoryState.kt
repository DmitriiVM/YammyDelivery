package com.dvm.menu.category.presentation.model

import com.dvm.database.api.models.CardDishDetails
import com.dvm.database.api.models.Subcategory

internal data class CategoryState(
    val title: Title? = null,
    val subcategories: List<Subcategory> = emptyList(),
    val selectedId: String? = null,
    val dishes: List<CardDishDetails> = emptyList(),
    val orderType: OrderType = OrderType.ALPHABET_ASC,
    val alert: Alert? = null
) {
    data class Alert(
        val text: Int,
        val argument: Any
    )
}
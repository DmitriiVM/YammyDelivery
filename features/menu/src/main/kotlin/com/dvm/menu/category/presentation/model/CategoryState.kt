package com.dvm.menu.category.presentation.model

import androidx.compose.runtime.Immutable
import com.dvm.database.Subcategory
import com.dvm.database.api.models.CardDish

@Immutable
internal data class CategoryState(
    val title: Title? = null,
    val subcategories: List<Subcategory> = emptyList(),
    val selectedId: String? = null,
    val dishes: List<CardDish> = emptyList(),
    val orderType: OrderType = OrderType.ALPHABET_ASC,
    val alert: Alert? = null
) {
    data class Alert(
        val text: Int,
        val argument: Any
    )
}
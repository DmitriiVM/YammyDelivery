package com.dvm.menu.category.presentation.model

import com.dvm.database.api.models.CardDishDetails
import com.dvm.database.api.models.Subcategory
import com.dvm.utils.Text

internal data class CategoryState(
    val title: Text? = null,
    val subcategories: List<Subcategory> = emptyList(),
    val selectedId: String? = null,
    val dishes: List<CardDishDetails> = emptyList(),
    val orderType: OrderType = OrderType.ALPHABET_ASC,
    val alert: Text? = null
)
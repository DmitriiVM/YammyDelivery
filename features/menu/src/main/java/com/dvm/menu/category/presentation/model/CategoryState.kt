package com.dvm.menu.category.presentation.model

import androidx.compose.runtime.Immutable
import com.dvm.db.api.models.CardDishDetails
import com.dvm.db.api.models.Subcategory

@Immutable
internal data class CategoryState(
    val title: String = "",
    val subcategories: List<Subcategory> = emptyList(),
    val selectedId: String? = null,
    val dishes: List<CardDishDetails> = emptyList(),
    val orderType: OrderType = OrderType.ALPHABET_ASC,
    val alert: String? = null
)
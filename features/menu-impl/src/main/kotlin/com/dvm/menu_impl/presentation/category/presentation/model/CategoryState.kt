package com.dvm.menu_impl.presentation.category.presentation.model

import androidx.compose.runtime.Immutable
import com.dvm.menu_api.domain.model.CardDish
import com.dvm.menu_api.domain.model.OrderType
import com.dvm.menu_api.domain.model.Subcategory
import com.dvm.utils.Text

@Immutable
internal data class CategoryState(
    val title: Text? = null,
    val subcategories: List<Subcategory> = emptyList(),
    val selectedId: String? = null,
    val dishes: List<CardDish> = emptyList(),
    val orderType: OrderType = OrderType.ALPHABET_ASC,
    val alert: Text? = null
)
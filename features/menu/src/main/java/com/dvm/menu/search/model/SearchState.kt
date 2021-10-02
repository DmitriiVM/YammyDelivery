package com.dvm.menu.search.model

import com.dvm.database.api.models.CardDishDetails
import com.dvm.database.api.models.ParentCategory
import com.dvm.database.api.models.Subcategory

internal data class SearchState(
    val query: String = "",
    val dishes: List<CardDishDetails> = emptyList(),
    val categories: List<ParentCategory> = emptyList(),
    val subcategories: List<Subcategory> = emptyList(),
    val hints: List<String> = emptyList(),
    val alert: Alert? = null
) {
    data class Alert(
        val text: Int,
        val argument: Any
    )
}
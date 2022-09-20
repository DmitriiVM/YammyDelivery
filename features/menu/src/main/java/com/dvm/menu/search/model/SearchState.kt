package com.dvm.menu.search.model

import androidx.compose.runtime.Immutable
import com.dvm.db.api.models.CardDishDetails
import com.dvm.db.api.models.ParentCategory
import com.dvm.db.api.models.Subcategory

@Immutable
internal data class SearchState(
    val query: String = "",
    val dishes: List<CardDishDetails> = emptyList(),
    val categories: List<ParentCategory> = emptyList(),
    val subcategories: List<Subcategory> = emptyList(),
    val hints: List<String> = emptyList(),
    val alert: String? = null
)
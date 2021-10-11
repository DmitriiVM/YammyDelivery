package com.dvm.menu.search.model

import androidx.compose.runtime.Immutable
import com.dvm.database.ParentCategory
import com.dvm.database.Subcategory
import com.dvm.database.api.models.CardDish
import com.dvm.utils.Text

@Immutable
internal data class SearchState(
    val query: String = "",
    val dishes: List<CardDish> = emptyList(),
    val categories: List<ParentCategory> = emptyList(),
    val subcategories: List<Subcategory> = emptyList(),
    val hints: List<String> = emptyList(),
    val alert: Text? = null
)
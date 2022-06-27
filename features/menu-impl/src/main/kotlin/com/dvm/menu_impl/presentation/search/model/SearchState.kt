package com.dvm.menu_impl.presentation.search.model

import androidx.compose.runtime.Immutable
import com.dvm.menu_api.domain.model.CardDish
import com.dvm.menu_api.domain.model.ParentCategory
import com.dvm.menu_api.domain.model.Subcategory
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
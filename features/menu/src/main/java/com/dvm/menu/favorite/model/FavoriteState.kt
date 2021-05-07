package com.dvm.menu.search.model

import androidx.compose.runtime.Immutable
import com.dvm.db.api.models.CategoryDish

@Immutable
internal data class FavoriteState(
    val dishes: List<CategoryDish> = emptyList(),
    val alertMessage: String? = null,
)
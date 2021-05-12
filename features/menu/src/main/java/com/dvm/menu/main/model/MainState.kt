package com.dvm.menu.search.model

import androidx.compose.runtime.Immutable
import com.dvm.db.api.models.CategoryDish

@Immutable
internal data class MainState(
    val recommended: List<CategoryDish> = emptyList(),
    val best: List<CategoryDish> = emptyList(),
    val popular: List<CategoryDish> = emptyList(),
    val alertMessage: String? = null,
)
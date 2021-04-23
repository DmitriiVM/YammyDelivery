package com.dvm.menu.search.model

import androidx.compose.runtime.Immutable
import com.dvm.db.db_api.data.models.CategoryDish

@Immutable
internal data class MainState(
    val alertMessage: String? = null,
    val recommended: List<CategoryDish> = emptyList(),
    val best: List<CategoryDish> = emptyList(),
    val popular: List<CategoryDish> = emptyList(),
)
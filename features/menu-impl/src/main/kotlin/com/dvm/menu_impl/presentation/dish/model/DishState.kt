package com.dvm.menu_impl.presentation.dish.model

import androidx.compose.runtime.Immutable
import com.dvm.menu_api.domain.model.DishDetails
import com.dvm.menu_api.domain.model.Review

@Immutable
internal data class DishState(
    val dish: DishDetails? = null,
    val quantity: Int = 1,
    val reviews: List<Review> = emptyList(),
    val alert: Int? = null,
    val reviewDialog: Boolean = false,
    val progress: Boolean = false
)
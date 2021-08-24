package com.dvm.dish.model

import androidx.compose.runtime.Immutable
import com.dvm.db.api.models.DishDetails
import com.dvm.db.api.models.Review

@Immutable
internal data class DishState(
    val dish: DishDetails? = null,
    val quantity: Int = 1,
    val reviews: List<Review> = emptyList(),
    val alert: String? = null,
    val reviewDialog: Boolean = false,
    val progress: Boolean = false
)
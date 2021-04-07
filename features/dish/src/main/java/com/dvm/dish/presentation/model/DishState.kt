package com.dvm.dish.presentation.model

import androidx.compose.runtime.Immutable
import com.dvm.db.db_api.data.models.DishDetails
import com.dvm.db.db_api.data.models.Review

@Immutable
internal data class DishState(
    val dish: DishDetails,
    val isFavorite: Boolean = false,
    val hasSpecialOffer: Boolean = false,
    val quantity: Int = 1,
    val reviews: List<Review> = emptyList()
)
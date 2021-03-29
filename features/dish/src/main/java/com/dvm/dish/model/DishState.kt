package com.dvm.dish.model

import androidx.compose.runtime.Immutable
import com.dvm.db.db_api.data.models.DishDetails
import com.dvm.db.db_api.data.models.Review

@Immutable
data class DishState(
    val dish: DishDetails,
    val isFavorite: Boolean = false,
    val hasSpecialOffer: Boolean = false,
    val quantity: Int = 1,
    val reviews: List<Review> = emptyList()
)
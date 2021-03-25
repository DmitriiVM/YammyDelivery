package com.dvm.dish.model

import androidx.compose.runtime.Immutable
import com.dvm.db.dao.DishDetails
import com.dvm.db.entities.Review

@Immutable
data class DishState(
    val dish: DishDetails,
    val isFavorite: Boolean = false,
    val hasSpecialOffer: Boolean = false,
    val quantity: Int = 1,
    val reviews: List<Review> = emptyList()
)
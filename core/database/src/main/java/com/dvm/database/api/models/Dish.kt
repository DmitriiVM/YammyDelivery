package com.dvm.database.api.models

import com.dvm.database.Review

data class DishDetails(
    val id: String,
    val name: String,
    val description: String?,
    val image: String,
    val oldPrice: Int,
    val price: Int,
    val rating: Double,
    val isFavorite: Boolean,
    val reviews: List<Review>
)

data class CardDish(
    val id: String,
    val name: String,
    val description: String?,
    val image: String,
    val oldPrice: Int,
    val price: Int,
    val rating: Double,
    val likes: Int,
    val isFavorite: Boolean
)
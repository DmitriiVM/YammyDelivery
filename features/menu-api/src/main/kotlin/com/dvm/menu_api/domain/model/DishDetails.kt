package com.dvm.menu_api.domain.model

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
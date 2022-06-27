package com.dvm.menu_api.domain.model

data class Dish(
    val id: String,
    val name: String,
    val description: String?,
    val image: String,
    val oldPrice: Int,
    val price: Int,
    val rating: Double,
    val commentsCount: Int,
    val likes: Int,
    val category: String,
    val isFavorite: Boolean
)
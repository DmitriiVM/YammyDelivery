package com.dvm.menu_api.domain.model

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
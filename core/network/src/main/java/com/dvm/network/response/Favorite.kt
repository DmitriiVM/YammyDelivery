package com.dvm.network.response

data class Favorite(
    val dishId: String,
    val favorite: Boolean,
    val updatedAt: Long
)
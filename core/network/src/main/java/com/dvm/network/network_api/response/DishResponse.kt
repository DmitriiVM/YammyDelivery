package com.dvm.network.network_api.response


data class DishResponse(
    val id: String,
    val name: String,
    val description: String,
    val image: String,
    val oldPrice: Int,
    val price: Int,
    val rating: Double,
    val commentsCount: Int,
    val likes: Int,
    val category: String,
    val active: Boolean,
    val createdAt: Long,
    val updatedAt: Long
)
package com.dvm.menu_impl.data.network.response

import kotlinx.serialization.Serializable

@Serializable
data class DishResponse(
    val id: String,
    val name: String,
    val description: String = "",
    val image: String,
    val oldPrice: Int = 0,
    val price: Int,
    val rating: Double,
    val commentsCount: Int,
    val likes: Int,
    val category: String,
    val active: Boolean,
    val createdAt: Long,
    val updatedAt: Long
)
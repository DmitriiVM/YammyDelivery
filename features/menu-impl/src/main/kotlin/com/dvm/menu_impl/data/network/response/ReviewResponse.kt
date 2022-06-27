package com.dvm.menu_impl.data.network.response

import kotlinx.serialization.Serializable

@Serializable
data class ReviewResponse(
    val dishId: String,
    val author: String,
    val date: String,
    val rating: Int,
    val text: String? = null,
    val active: Boolean,
    val createdAt: Long,
    val updatedAt: Long
)
package com.dvm.network.response

data class ReviewResponse(
    val dishId: String,
    val author: String,
    val date: String,
    val rating: Int,
    val text: String,
    val active: Boolean,
    val createdAt: Long,
    val updatedAt: Long
)
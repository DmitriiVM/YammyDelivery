package com.dvm.network.api.response

import kotlinx.serialization.Serializable

@Serializable
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
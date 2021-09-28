package com.dvm.network.api.response

import kotlinx.serialization.Serializable

@Serializable
data class OrderItemResponse(
    val name: String,
    val image: String,
    val amount: Int,
    val price: Int,
    val dishId: String
)
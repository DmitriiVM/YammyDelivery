package com.dvm.network.response

data class OrderItemResponse(
    val name: String,
    val image: String,
    val amount: Int,
    val price: Int,
    val dishId: String
)
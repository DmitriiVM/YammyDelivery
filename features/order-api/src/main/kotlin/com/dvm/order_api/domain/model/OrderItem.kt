package com.dvm.order_api.domain.model

data class OrderItem(
    val name: String,
    val orderId: String,
    val image: String,
    val amount: Int,
    val price: Int,
    val dishId: String
)
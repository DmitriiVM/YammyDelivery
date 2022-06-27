package com.dvm.order_impl.data.network.response

import kotlinx.serialization.Serializable

@Serializable
data class OrderResponse(
    val id: String,
    val total: Int,
    val address: String,
    val statusId: String,
    val active: Boolean,
    val completed: Boolean,
    val createdAt: Long,
    val updatedAt: Long,
    val items: List<OrderItemResponse>
)

@Serializable
data class OrderItemResponse(
    val name: String,
    val image: String,
    val amount: Int,
    val price: Int,
    val dishId: String
)
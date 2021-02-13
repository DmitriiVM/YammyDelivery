package com.dvm.network.response

data class Order(
    val id: String,
    val total: Int,
    val address: String,
    val statusId: Int,
    val active: Boolean,
    val completed: Boolean,
    val createdAt: Long,
    val updatedAt: Long,
    val items: List<OrderItem>
)
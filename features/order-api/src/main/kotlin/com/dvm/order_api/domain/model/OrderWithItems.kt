package com.dvm.order_api.domain.model

import java.util.*

data class OrderWithItems(
    val id: String,
    val createdAt: Date,
    val total: Int,
    val address: String,
    val statusId: String,
    val completed: Boolean,
    val status: OrderStatus,
    val items: List<OrderItem>
)
package com.dvm.database.api.models

import com.dvm.database.OrderItem
import com.dvm.database.OrderStatus
import java.util.*

data class Order(
    val id: String,
    val createdAt: Date,
    val total: Int,
    val address: String,
    val status: String,
)

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
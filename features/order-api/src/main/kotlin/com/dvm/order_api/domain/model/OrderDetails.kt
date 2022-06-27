package com.dvm.order_api.domain.model

import java.util.*

data class OrderDetails(
    val id: String,
    val total: Int,
    val address: String,
    val statusId: String,
    val active: Boolean?,
    val completed: Boolean,
    val createdAt: Date,
    val items: List<OrderItem>
)
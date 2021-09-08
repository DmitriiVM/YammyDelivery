package com.dvm.order.orders.model

import com.dvm.db.api.models.OrderData

internal data class OrdersState(
    val status: OrderStatus = OrderStatus.ACTUAL,
    val orders: List<OrderData> = emptyList(),
    val empty: Boolean = false,
    val progress: Boolean = false,
    val alert: String? = null
)

internal enum class OrderStatus{
    ACTUAL,
    COMPLETED
}
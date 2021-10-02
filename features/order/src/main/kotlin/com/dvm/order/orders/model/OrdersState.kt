package com.dvm.order.orders.model

import com.dvm.database.api.models.OrderData

internal data class OrdersState(
    val status: OrderStatus = OrderStatus.ACTUAL,
    val orders: List<OrderData> = emptyList(),
    val empty: Boolean = false,
    val progress: Boolean = false,
    val alert: Int? = null
)

internal enum class OrderStatus{
    ACTUAL,
    COMPLETED
}
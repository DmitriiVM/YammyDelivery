package com.dvm.order.orders.model

import androidx.compose.runtime.Immutable
import com.dvm.database.api.models.Order

@Immutable
internal data class OrdersState(
    val status: OrderStatus = OrderStatus.ACTUAL,
    val orders: List<Order> = emptyList(),
    val empty: Boolean = false,
    val progress: Boolean = false,
    val alert: Int? = null
)

internal enum class OrderStatus {
    ACTUAL,
    COMPLETED
}
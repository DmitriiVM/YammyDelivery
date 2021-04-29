package com.dvm.order.orders.model

import com.dvm.db.db_api.data.models.OrderData
import javax.annotation.concurrent.Immutable

@Immutable
internal data class OrdersState(
    val status: OrderStatus = OrderStatus.ACTUAL,
    val orders: List<OrderData> = emptyList()
)

internal enum class OrderStatus{
    ACTUAL,
    COMPLETED
}
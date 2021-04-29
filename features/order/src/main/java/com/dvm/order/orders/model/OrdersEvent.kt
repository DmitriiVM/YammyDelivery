package com.dvm.order.orders.model

internal sealed class OrdersEvent {
    data class SelectStatus(val status: OrderStatus) : OrdersEvent()
    class OrderClick(val orderId: String) : OrdersEvent()
    object BackClick: OrdersEvent()
}
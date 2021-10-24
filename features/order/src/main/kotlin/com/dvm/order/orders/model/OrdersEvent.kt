package com.dvm.order.orders.model

internal sealed class OrdersEvent {
    data class StatusSelect(val status: OrderStatus) : OrdersEvent()
    data class Order(val orderId: String) : OrdersEvent()
    object Back : OrdersEvent()
}
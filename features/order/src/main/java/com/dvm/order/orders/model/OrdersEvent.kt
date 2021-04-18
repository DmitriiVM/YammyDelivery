package com.dvm.order.orders.model

internal sealed class OrdersEvent {
    object DismissAlert : OrdersEvent()
    object BackClick: OrdersEvent()
}
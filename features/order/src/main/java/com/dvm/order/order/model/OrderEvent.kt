package com.dvm.order.order.model

internal sealed class OrderEvent {
    object DismissAlert : OrderEvent()
    object BackClick: OrderEvent()
}
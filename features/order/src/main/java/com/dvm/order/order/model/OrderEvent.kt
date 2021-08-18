package com.dvm.order.order.model

internal sealed class OrderEvent {
    object CancelOrder : OrderEvent()
    object TryOrderAgain : OrderEvent()
    object OrderAgain : OrderEvent()
    object DismissAlert : OrderEvent()
    object CancelOrdering : OrderEvent()
    object BackClick: OrderEvent()
}
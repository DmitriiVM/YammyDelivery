package com.dvm.order.order.model

internal sealed class OrderEvent {
    object CancelOrder : OrderEvent()
    object OrderAgainClick : OrderEvent()
    object OrderAgain : OrderEvent()
    object DismissAlert : OrderEvent()
    object OrderCanceled : OrderEvent()
    object BackClick: OrderEvent()
}
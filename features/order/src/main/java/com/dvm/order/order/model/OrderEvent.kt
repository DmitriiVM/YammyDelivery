package com.dvm.order.order.model

internal sealed class OrderEvent {
    object CancelOrder : OrderEvent()
    object DismissAlert : OrderEvent()
    object BackClick: OrderEvent()
    object ReorderClick : OrderEvent()
    object OrderAgain : OrderEvent()
}
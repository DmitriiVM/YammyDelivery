package com.dvm.order_impl.presentation.order.model

internal sealed class OrderEvent {
    object CancelOrder : OrderEvent()
    object TryOrderAgain : OrderEvent()
    object OrderAgain : OrderEvent()
    object DismissAlert : OrderEvent()
    object CancelOrdering : OrderEvent()
    object Back : OrderEvent()
}
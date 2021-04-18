package com.dvm.order.ordering.model

internal sealed class OrderingEvent {
    data class MakeOrder(val fields: OrderingFields) : OrderingEvent()
    object MapButtonClick : OrderingEvent()
    object DismissAlert : OrderingEvent()
    object BackClick : OrderingEvent()
}
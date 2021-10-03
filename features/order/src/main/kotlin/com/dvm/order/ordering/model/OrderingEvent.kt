package com.dvm.order.ordering.model

internal sealed class OrderingEvent {
    data class MakeOrder(val fields: OrderingFields) : OrderingEvent()
    data class ChangeAddress(val address: String) : OrderingEvent()
    object OpenMap : OrderingEvent()
    object DismissAlert : OrderingEvent()
    object Back : OrderingEvent()
}
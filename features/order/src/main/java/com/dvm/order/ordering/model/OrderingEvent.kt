package com.dvm.order.ordering.model

internal sealed class OrderingEvent {
    data class MakeOrder(val fields: OrderingFields) : OrderingEvent()
    data class AddressChanged(val address: String) : OrderingEvent()
    object MapButtonClick : OrderingEvent()
    object DismissAlert : OrderingEvent()
    object BackClick : OrderingEvent()
}
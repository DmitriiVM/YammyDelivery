package com.dvm.order.orders.model

import javax.annotation.concurrent.Immutable

@Immutable
internal data class OrdersState(
    val alertMessage: String? = null
)
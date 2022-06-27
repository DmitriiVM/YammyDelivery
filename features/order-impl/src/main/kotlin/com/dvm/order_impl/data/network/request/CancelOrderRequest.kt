package com.dvm.order_impl.data.network.request

import kotlinx.serialization.Serializable

@Serializable
internal class CancelOrderRequest(
    val orderId: String
)
package com.dvm.network.impl.request

import kotlinx.serialization.Serializable

@Serializable
internal class CancelOrderRequest(
    val orderId: String
)
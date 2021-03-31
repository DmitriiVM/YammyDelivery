package com.dvm.network.network_impl.request

internal class UpdateCartRequest(
    val promocode: String,
    val items: Map<String, Int>
)
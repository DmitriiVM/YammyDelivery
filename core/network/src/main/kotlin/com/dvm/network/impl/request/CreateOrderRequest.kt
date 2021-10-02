package com.dvm.network.impl.request

internal data class CreateOrderRequest(
    val address: String,
    val entrance: Int?,
    val floor: Int?,
    val apartment: String?,
    val intercom: String?,
    val comment: String?
)
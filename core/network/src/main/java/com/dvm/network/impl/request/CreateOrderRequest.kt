package com.dvm.network.impl.request

import kotlinx.serialization.Serializable

@Serializable
internal data class CreateOrderRequest(
    val address: String,
    val entrance: Int?,
    val floor: Int?,
    val apartment: String?,
    val intercom: String?,
    val comment: String?
)
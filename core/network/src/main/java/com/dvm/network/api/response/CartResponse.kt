package com.dvm.network.api.response

import kotlinx.serialization.Serializable

@Serializable
data class CartResponse(
    val promocode: String,
    val promotext: String,
    val total: Int,
    val items: List<CartItemResponse>
)
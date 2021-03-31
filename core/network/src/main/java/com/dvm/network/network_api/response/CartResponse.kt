package com.dvm.network.network_api.response

data class CartResponse(
    val promocode: String,
    val promotext: String,
    val total: Int,
    val items: List<CartItemResponse>
)
package com.dvm.network.response

data class CartResponse(
    val promocode: String,
    val promotext: String,
    val total: Int,
    val items: List<CartItemResponse>
)
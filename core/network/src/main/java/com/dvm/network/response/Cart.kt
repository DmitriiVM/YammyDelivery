package com.dvm.network.response

data class Cart(
    val promocode: String,
    val promotext: String,
    val total: Int,
    val items: List<CartItem>
)
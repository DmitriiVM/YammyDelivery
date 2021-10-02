package com.dvm.network.impl.request

internal class UpdateCartRequest(
    val promocode: String,
    val items: List<CartItem>
)

internal class CartItem(
    val id: String,
    val amount: Int
)
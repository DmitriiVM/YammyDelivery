package com.dvm.cart_impl.data.network.request

import kotlinx.serialization.Serializable

@Serializable
internal class UpdateCartRequest(
    val promocode: String,
    val items: List<CartItem>
)

@Serializable
internal class CartItem(
    val id: String,
    val amount: Int
)
package com.dvm.cart_impl.data.network.response

import kotlinx.serialization.Serializable

@Serializable
data class CartResponse(
    val promocode: String,
    val promotext: String,
    val total: Int,
    val items: List<CartItemResponse>
)
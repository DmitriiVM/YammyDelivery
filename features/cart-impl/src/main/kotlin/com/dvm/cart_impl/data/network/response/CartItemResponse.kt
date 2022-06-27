package com.dvm.cart_impl.data.network.response

import kotlinx.serialization.Serializable

@Serializable
data class CartItemResponse(
    val id: String,
    val amount: Int,
    val price: Int
)
package com.dvm.cart_api.domain.model

data class Cart(
    val promocode: String,
    val promotext: String,
    val total: Int,
    val items: List<CartItemDetail>
)

data class CartItemDetail(
    val id: String,
    val amount: Int,
    val price: Int
)
package com.dvm.cart_api.domain.model

data class CartDishItem(
    val dishId: String,
    val quantity: Int,
    val name: String,
    val image: String,
    val price: Int
)
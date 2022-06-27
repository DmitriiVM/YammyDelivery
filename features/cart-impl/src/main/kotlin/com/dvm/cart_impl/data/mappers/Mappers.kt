package com.dvm.cart_impl.data.mappers

import com.dvm.cart_api.domain.model.Address
import com.dvm.cart_api.domain.model.Cart
import com.dvm.cart_api.domain.model.CartItem
import com.dvm.cart_api.domain.model.CartItemDetail
import com.dvm.cart_impl.data.network.response.AddressResponse
import com.dvm.cart_impl.data.network.response.CartItemResponse
import com.dvm.cart_impl.data.network.response.CartResponse
import com.dvm.cartdatabase.CartItemEntity

internal fun List<CartItemEntity>.toCartItems() =
    map {
        CartItem(
            dishId = it.dishId,
            quantity = it.quantity,
        )
    }


internal fun CartItem.toCartItemEntity() =
    CartItemEntity(
        dishId = dishId,
        quantity = quantity,
    )

internal fun CartResponse.toCart() =
    Cart(
        promocode = promocode,
        promotext = promotext,
        total = total,
        items = items.map { it.toCartItemDetail() },
    )

internal fun CartItemResponse.toCartItemDetail() =
    CartItemDetail(
        id = id,
        amount = amount,
        price = price
    )

internal fun AddressResponse.toAddress() =
    Address(
        city = city,
        street = street,
        house = house
    )
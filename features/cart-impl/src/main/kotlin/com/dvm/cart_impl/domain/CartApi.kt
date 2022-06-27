package com.dvm.cart_impl.domain

import com.dvm.cart_api.domain.model.Address
import com.dvm.cart_api.domain.model.Cart

internal interface CartApi {

    suspend fun getCart(token: String): Cart

    suspend fun updateCart(
        token: String,
        promocode: String,
        items: Map<String, Int>
    ): Cart

    suspend fun checkInput(address: String): Address

    suspend fun checkCoordinates(
        latitude: Double,
        longitude: Double
    ): Address
}
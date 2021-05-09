package com.dvm.network.api

import com.dvm.network.api.response.AddressResponse
import com.dvm.network.api.response.CartResponse

interface CartApi {

    suspend fun getCart(token: String): CartResponse

    suspend fun updateCart(
        token: String,
        promocode: String,
        items: Map<String, Int>
    ): CartResponse

    suspend fun checkInput(address: String): AddressResponse

    suspend fun checkCoordinates(
        latitude: Long,
        longitude: Long
    ): AddressResponse
}
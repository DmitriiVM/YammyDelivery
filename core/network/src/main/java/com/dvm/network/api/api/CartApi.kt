package com.dvm.network.api.api

import com.dvm.network.api.response.AddressResponse
import com.dvm.network.api.response.CartResponse

interface CartApi {

    suspend fun getCart(): CartResponse

    suspend fun updateCart(
        promocode: String,
        items: Map<String, Int>
    ): CartResponse

    suspend fun checkInput(address: String): AddressResponse

    suspend fun checkCoordinates(
        lat: Long,
        lon: Long
    ): AddressResponse
}
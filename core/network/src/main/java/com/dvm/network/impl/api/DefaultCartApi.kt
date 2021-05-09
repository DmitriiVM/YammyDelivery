package com.dvm.network.impl.api

import com.dvm.network.api.CartApi
import com.dvm.network.api.response.AddressResponse
import com.dvm.network.api.response.CartResponse
import com.dvm.network.impl.ApiService
import com.dvm.network.impl.request.CartItem
import com.dvm.network.impl.request.CheckCoordinatesRequest
import com.dvm.network.impl.request.CheckInputRequest
import com.dvm.network.impl.request.UpdateCartRequest
import javax.inject.Inject

internal class DefaultCartApi @Inject constructor(
    private val apiService: ApiService
) : CartApi {

    override suspend fun getCart(token: String): CartResponse =
        apiService.getCart(token)

    override suspend fun updateCart(
        token: String,
        promocode: String,
        items: Map<String, Int>
    ): CartResponse =
        apiService.updateCart(
            token = token,
            updateCartRequest = UpdateCartRequest(
                promocode = promocode,
                items = items.map { item ->
                    CartItem(
                        id = item.key,
                        amount = item.value
                    )
                }
            )
        )

    override suspend fun checkCoordinates(
        latitude: Long,
        longitude: Long
    ): AddressResponse =
        apiService.checkCoordinates(
            CheckCoordinatesRequest(latitude, longitude)
        )

    override suspend fun checkInput(address: String): AddressResponse =
        apiService.checkInput(CheckInputRequest(address))
}
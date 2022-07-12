package com.dvm.cart_impl.data.network

import com.dvm.cart_api.domain.model.Address
import com.dvm.cart_api.domain.model.Cart
import com.dvm.cart_impl.data.mappers.toAddress
import com.dvm.cart_impl.data.mappers.toCart
import com.dvm.cart_impl.data.network.request.CartItem
import com.dvm.cart_impl.data.network.request.CheckCoordinatesRequest
import com.dvm.cart_impl.data.network.request.CheckInputRequest
import com.dvm.cart_impl.data.network.request.UpdateCartRequest
import com.dvm.cart_impl.data.network.response.AddressResponse
import com.dvm.cart_impl.data.network.response.CartResponse
import com.dvm.cart_impl.domain.CartApi
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

internal class DefaultCartApi(
    private val client: HttpClient
) : CartApi {

    override suspend fun getCart(token: String): Cart =
        client.get("cart") {
            header(HttpHeaders.Authorization, token)
        }
            .body<CartResponse>()
            .toCart()

    override suspend fun updateCart(
        token: String,
        promocode: String,
        items: Map<String, Int>
    ): Cart =
        client.put("cart") {
            header(HttpHeaders.Authorization, token)
            setBody(
                UpdateCartRequest(
                    promocode = promocode,
                    items = items.map { item ->
                        CartItem(
                            id = item.key,
                            amount = item.value
                        )
                    }
                )
            )
        }
            .body<CartResponse>()
            .toCart()

    override suspend fun checkCoordinates(
        latitude: Double,
        longitude: Double
    ): Address =
        client.post("address/coordinates") {
            setBody(CheckCoordinatesRequest(latitude, longitude))
        }
            .body<AddressResponse>()
            .toAddress()

    override suspend fun checkInput(address: String): Address =
        client.post("address/input") {
            setBody(CheckInputRequest(address))
        }
            .body<AddressResponse>()
            .toAddress()
}
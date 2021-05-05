package com.dvm.network.impl.api

import com.dvm.network.api.api.CartApi
import com.dvm.network.api.response.AddressResponse
import com.dvm.network.api.response.CartResponse
import com.dvm.network.impl.ApiService
import com.dvm.network.impl.api
import com.dvm.network.impl.request.CartItem
import com.dvm.network.impl.request.CheckCoordinatesRequest
import com.dvm.network.impl.request.CheckInputRequest
import com.dvm.network.impl.request.UpdateCartRequest
import com.dvm.preferences.api.data.DatastoreRepository
import javax.inject.Inject

internal class DefaultCartApi @Inject constructor(
    private val apiService: ApiService,
    private val datastore: DatastoreRepository
) : CartApi {

    override suspend fun getCart(): CartResponse =
        api {
            apiService.getCart(getAccessToken())
        }

    override suspend fun updateCart(
        promocode: String,
        items: Map<String, Int>
    ): CartResponse =
        api {
            apiService.updateCart(
                token = getAccessToken(),
                updateCartRequest = UpdateCartRequest(
                    promocode = promocode,
                    items = items.map {
                        CartItem(
                            id = it.key,
                            amount = it.value
                        )
                    }
                )
            )
        }

    override suspend fun checkInput(address: String): AddressResponse =
        api {
            apiService.checkInput(
                checkInputRequest = CheckInputRequest(address)
            )
        }

    override suspend fun checkCoordinates(lat: Long, lon: Long): AddressResponse =
        api {
            apiService.checkCoordinates(
                checkCoordinatesRequest = CheckCoordinatesRequest(lat, lon)
            )
        }

    private suspend fun getAccessToken() =
        api {
            requireNotNull(datastore.getAccessToken())
        }
}
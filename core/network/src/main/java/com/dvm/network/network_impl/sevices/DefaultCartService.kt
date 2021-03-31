package com.dvm.network.network_impl.sevices

import com.dvm.network.network_api.response.AddressResponse
import com.dvm.network.network_api.response.CartResponse
import com.dvm.network.network_api.services.CartService
import com.dvm.network.network_impl.ApiService
import com.dvm.network.network_impl.request.CheckCoordinatesRequest
import com.dvm.network.network_impl.request.CheckInputRequest
import com.dvm.network.network_impl.request.UpdateCartRequest
import com.dvm.preferences.datastore_api.data.DatastoreRepository
import javax.inject.Inject

internal class DefaultCartService @Inject constructor(
    private val apiService: ApiService,
    private val datastore: DatastoreRepository
): CartService {

    override suspend fun getCart(): CartResponse = apiService.getCart(getAccessToken())

    override suspend fun updateCart(
        promocode: String,
        items: Map<String, Int>
    ): CartResponse =
        apiService.updateCart(
            token = getAccessToken(),
            updateCartRequest = UpdateCartRequest(
                promocode = promocode,
                items = items
            )
        )

    override suspend fun checkInput(address: String): AddressResponse =
        apiService.checkInput(
            checkInputRequest = CheckInputRequest(address)
        )

    override suspend fun checkCoordinates(lat: Long, lon: Long): AddressResponse =
        apiService.checkCoordinates(
            checkCoordinatesRequest = CheckCoordinatesRequest(lat, lon)
        )

    private suspend fun getAccessToken() = requireNotNull(datastore.getAccessToken())
}
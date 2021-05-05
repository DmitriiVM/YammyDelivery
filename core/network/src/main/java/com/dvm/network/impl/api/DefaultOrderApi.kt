package com.dvm.network.impl.api

import com.dvm.network.api.api.OrderApi
import com.dvm.network.api.response.OrderResponse
import com.dvm.network.api.response.StatusResponse
import com.dvm.network.impl.ApiService
import com.dvm.network.impl.api
import com.dvm.network.impl.request.CancelOrderRequest
import com.dvm.network.impl.request.CreateOrderRequest
import com.dvm.preferences.api.data.DatastoreRepository
import javax.inject.Inject

internal class DefaultOrderApi @Inject constructor(
    private val apiService: ApiService,
    private val datastore: DatastoreRepository
) : OrderApi {

    override suspend fun createOrder(
        address: String,
        entrance: Int?,
        floor: Int?,
        apartment: String?,
        intercom: String?,
        comment: String?,
    ): OrderResponse =
        api {
            apiService.createOrder(
                token = getAccessToken(),
                createOrderRequest = CreateOrderRequest(
                    address = address,
                    entrance = entrance,
                    floor = floor,
                    apartment = apartment,
                    intercom = intercom,
                    comment = comment,
                )
            )
        }

    override suspend fun getOrders(): List<OrderResponse> =
        api {
            apiService.getOrders(getAccessToken())
        }

    override suspend fun getStatuses(): List<StatusResponse>  =
        api {
            apiService.getStatuses()
        }

    override suspend fun cancelOrder(orderId: String): OrderResponse =
        api {
            apiService.cancelOrder(
                token = getAccessToken(),
                cancelOrderRequest = CancelOrderRequest(orderId)
            )
        }

    private suspend fun getAccessToken() = requireNotNull(datastore.getAccessToken())
}
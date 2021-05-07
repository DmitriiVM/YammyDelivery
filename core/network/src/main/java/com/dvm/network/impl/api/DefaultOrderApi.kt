package com.dvm.network.impl.api

import com.dvm.network.api.OrderApi
import com.dvm.network.api.response.OrderResponse
import com.dvm.network.api.response.StatusResponse
import com.dvm.network.impl.ApiService
import com.dvm.network.impl.request.CancelOrderRequest
import com.dvm.network.impl.request.CreateOrderRequest
import com.dvm.preferences.api.DatastoreRepository
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

    override suspend fun getOrders(limit: Int?): List<OrderResponse> =
        apiService.getOrders(
            token = getAccessToken(),
            ifModifiedSince = datastore.getLastUpdateTime(),
            limit = limit
        )

    override suspend fun getStatuses(): List<StatusResponse> =
        apiService.getStatuses(datastore.getLastUpdateTime())

    override suspend fun cancelOrder(orderId: String): OrderResponse =
        apiService.cancelOrder(
            token = getAccessToken(),
            cancelOrderRequest = CancelOrderRequest(orderId)
        )

    private suspend fun getAccessToken() = requireNotNull(datastore.getAccessToken())
}
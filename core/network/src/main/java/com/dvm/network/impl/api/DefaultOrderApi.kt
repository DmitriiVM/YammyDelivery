package com.dvm.network.impl.api

import com.dvm.network.api.OrderApi
import com.dvm.network.api.response.OrderResponse
import com.dvm.network.api.response.StatusResponse
import com.dvm.network.impl.ApiService
import com.dvm.network.impl.getModified
import com.dvm.network.impl.request.CancelOrderRequest
import com.dvm.network.impl.request.CreateOrderRequest
import javax.inject.Inject

internal class DefaultOrderApi @Inject constructor(
    private val apiService: ApiService
) : OrderApi {

    override suspend fun createOrder(
        token: String,
        address: String,
        entrance: Int?,
        floor: Int?,
        apartment: String?,
        intercom: String?,
        comment: String?,
    ): OrderResponse =
        apiService.createOrder(
            token = token,
            createOrderRequest = CreateOrderRequest(
                address = address,
                entrance = entrance,
                floor = floor,
                apartment = apartment,
                intercom = intercom,
                comment = comment,
            )
        )

    override suspend fun getOrders(
        token: String,
        lastUpdateTime: Long?,
        limit: Int?
    ): List<OrderResponse> = getModified {
        apiService.getOrders(
            token = token,
            ifModifiedSince = lastUpdateTime,
            limit = limit
        )
    } ?: emptyList()

    override suspend fun getStatuses(
        lastUpdateTime: Long?
    ): List<StatusResponse> = getModified {
        apiService.getStatuses(lastUpdateTime)
    } ?: emptyList()

    override suspend fun cancelOrder(
        token: String,
        orderId: String
    ): OrderResponse =
        apiService.cancelOrder(
            token = token,
            cancelOrderRequest = CancelOrderRequest(orderId)
        )
}
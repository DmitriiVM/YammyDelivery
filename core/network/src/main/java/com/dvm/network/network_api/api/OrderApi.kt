package com.dvm.network.network_api.api

import com.dvm.network.network_api.response.OrderResponse
import com.dvm.network.network_api.response.StatusResponse

interface OrderApi {

    suspend fun createOrder(
        address: String,
        entrance: Int?,
        floor: Int?,
        apartment: String?,
        intercom: String?,
        comment: String?,
    ): OrderResponse

    suspend fun getOrders(): List<OrderResponse>

    suspend fun getStatuses(): List<StatusResponse>

    suspend fun cancelOrder(orderId: Int): OrderResponse
}
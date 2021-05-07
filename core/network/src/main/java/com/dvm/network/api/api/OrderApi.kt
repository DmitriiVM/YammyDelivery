package com.dvm.network.api.api

import com.dvm.network.api.response.OrderResponse
import com.dvm.network.api.response.StatusResponse

interface OrderApi {

    suspend fun createOrder(
        address: String,
        entrance: Int?,
        floor: Int?,
        apartment: String?,
        intercom: String?,
        comment: String?,
    ): OrderResponse

    suspend fun getOrders(limit: Int? = 500): List<OrderResponse>

    suspend fun getStatuses(): List<StatusResponse>

    suspend fun cancelOrder(orderId: String): OrderResponse
}
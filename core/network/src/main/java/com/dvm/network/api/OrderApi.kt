package com.dvm.network.api

import com.dvm.network.api.response.OrderResponse
import com.dvm.network.api.response.StatusResponse

interface OrderApi {

    suspend fun createOrder(
        token: String,
        address: String,
        entrance: Int?,
        floor: Int?,
        apartment: String?,
        intercom: String?,
        comment: String?,
    ): OrderResponse

    suspend fun getOrders(
        token: String,
        lastUpdateTime: Long?,
        limit: Int? = 500
    ): List<OrderResponse>

    suspend fun getStatuses(
        lastUpdateTime: Long?
    ): List<StatusResponse>

    suspend fun cancelOrder(
        token: String,
        orderId: String
    ): OrderResponse
}
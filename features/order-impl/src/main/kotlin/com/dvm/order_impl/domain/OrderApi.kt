package com.dvm.order_impl.domain

import com.dvm.order_api.domain.model.OrderDetails
import com.dvm.order_api.domain.model.OrderStatus

internal interface OrderApi {

    suspend fun createOrder(
        token: String,
        address: String,
        entrance: Int?,
        floor: Int?,
        apartment: String?,
        intercom: String?,
        comment: String?,
    ): OrderDetails

    suspend fun getOrders(
        token: String,
        lastUpdateTime: Long?
    ): List<OrderDetails>

    suspend fun getStatuses(
        lastUpdateTime: Long?
    ): List<OrderStatus>

    suspend fun cancelOrder(
        token: String,
        orderId: String
    ): OrderDetails
}
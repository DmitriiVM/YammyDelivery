package com.dvm.order_api.domain

import com.dvm.order_api.domain.model.Order
import com.dvm.order_api.domain.model.OrderDetails
import com.dvm.order_api.domain.model.OrderId
import com.dvm.order_api.domain.model.OrderItem
import com.dvm.order_api.domain.model.OrderStatus
import com.dvm.order_api.domain.model.OrderWithItems
import kotlinx.coroutines.flow.Flow

interface OrderInteractor {
    fun activeOrders(): Flow<List<Order>>
    fun completedOrders(): Flow<List<Order>>
    fun order(orderId: String): Flow<OrderWithItems>
    suspend fun deleteInactiveOrders()
    suspend fun insertOrders(orders: List<OrderDetails>)
    suspend fun insertOrderItems(orderItems: List<OrderItem>)
    suspend fun insertOrderStatuses(orderStatuses: List<OrderStatus>)
    suspend fun deleteOrders()
    suspend fun updateOrders()
    suspend fun cancelOrder(orderId: String)
    suspend fun createOrder(
        address: String,
        entrance: Int?,
        floor: Int?,
        apartment: String?,
        intercom: String?,
        comment: String?,
    ): OrderId
}
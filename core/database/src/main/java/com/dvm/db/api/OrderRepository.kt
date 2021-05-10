package com.dvm.db.api

import com.dvm.db.api.models.*
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    fun activeOrders(): Flow<List<OrderData>>
    fun completedOrders(): Flow<List<OrderData>>
    fun order(orderId: String): Flow<OrderWithItems>
    suspend fun deleteInactiveOrders()
    suspend fun insertOrders(orders: List<Order>)
    suspend fun insertOrderItems(orderItems: List<OrderItem>)
    suspend fun insertOrderStatuses(orderStatuses: List<OrderStatus>)
}
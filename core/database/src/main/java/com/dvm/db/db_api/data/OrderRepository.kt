package com.dvm.db.db_api.data

import com.dvm.db.db_api.data.models.*
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    fun activeOrders(): Flow<List<OrderData>>
    fun completedOrders(): Flow<List<OrderData>>
    fun order(orderId: String): Flow<OrderWithItems>
    suspend fun insertOrders(orders: List<Order>)
    suspend fun insertOrderItems(orderItems: List<OrderItem>)
    suspend fun insertOrderStatuses(orderStatuses: List<OrderStatus>)
}
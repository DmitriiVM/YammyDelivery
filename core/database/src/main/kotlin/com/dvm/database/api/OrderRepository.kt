package com.dvm.database.api

import com.dvm.database.api.models.Order
import com.dvm.database.api.models.OrderData
import com.dvm.database.api.models.OrderItem
import com.dvm.database.api.models.OrderStatus
import com.dvm.database.api.models.OrderWithItems
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    fun activeOrders(): Flow<List<OrderData>>
    fun completedOrders(): Flow<List<OrderData>>
    fun order(orderId: String): Flow<OrderWithItems>
    suspend fun deleteInactiveOrders()
    suspend fun insertOrders(orders: List<Order>)
    suspend fun insertOrderItems(orderItems: List<OrderItem>)
    suspend fun insertOrderStatuses(orderStatuses: List<OrderStatus>)
    suspend fun deleteOrders()
}
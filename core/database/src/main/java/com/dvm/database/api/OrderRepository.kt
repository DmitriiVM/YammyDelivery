package com.dvm.database.api

import com.dvm.database.OrderDetails
import com.dvm.database.OrderItem
import com.dvm.database.OrderStatus
import com.dvm.database.api.models.Order
import com.dvm.database.api.models.OrderWithItems
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    fun activeOrders(): Flow<List<Order>>
    fun completedOrders(): Flow<List<Order>>
    fun order(orderId: String): Flow<OrderWithItems>
    suspend fun deleteInactiveOrders()
    suspend fun insertOrders(orders: List<OrderDetails>)
    suspend fun insertOrderItems(orderItems: List<OrderItem>)
    suspend fun insertOrderStatuses(orderStatuses: List<OrderStatus>)
    suspend fun deleteOrders()
}
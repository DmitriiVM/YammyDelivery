package com.dvm.order_impl.domain

import com.dvm.order_api.domain.model.Order
import com.dvm.order_api.domain.model.OrderDetails
import com.dvm.order_api.domain.model.OrderItem
import com.dvm.order_api.domain.model.OrderStatus
import com.dvm.order_api.domain.model.OrderWithItems
import kotlinx.coroutines.flow.Flow

internal interface OrderRepository {
    fun activeOrders(): Flow<List<Order>>
    fun completedOrders(): Flow<List<Order>>
    fun order(orderId: String): Flow<OrderWithItems>
    suspend fun deleteInactiveOrders()
    suspend fun insertOrders(orders: List<OrderDetails>)
    suspend fun insertOrderItems(orderItems: List<OrderItem>)
    suspend fun insertOrderStatuses(orderStatuses: List<OrderStatus>)
    suspend fun deleteOrders()
}
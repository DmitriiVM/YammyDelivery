package com.dvm.database.impl.repositories

import com.dvm.database.OrderDetails
import com.dvm.database.OrderDetailsQueries
import com.dvm.database.OrderItem
import com.dvm.database.OrderItemQueries
import com.dvm.database.OrderStatus
import com.dvm.database.OrderStatusQueries
import com.dvm.database.api.OrderRepository
import com.dvm.database.api.models.Order
import com.dvm.database.api.models.OrderWithItems
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOne
import java.util.Date
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.withContext

internal class DefaultOrderRepository(
    private val orderQueries: OrderDetailsQueries,
    private val orderItemQueries: OrderItemQueries,
    private val orderStatusQueries: OrderStatusQueries
) : OrderRepository {

    private val orderMapper: (String, Int, String, Date, String) -> Order =
        { id, total, address, createdAt, status ->
            Order(
                id = id,
                total = total,
                address = address,
                createdAt = createdAt,
                status = status
            )
        }

    override fun activeOrders(): Flow<List<Order>> =
        orderQueries
            .activeOrders(orderMapper)
            .asFlow()
            .mapToList(Dispatchers.IO)

    override fun completedOrders(): Flow<List<Order>> =
        orderQueries
            .completedOrders(orderMapper)
            .asFlow()
            .mapToList(Dispatchers.IO)

    override fun order(orderId: String) =
        orderQueries
            .order(orderId)
            .asFlow()
            .mapToOne(Dispatchers.IO)
            .flatMapLatest { order ->
                combine(
                    orderStatusQueries
                        .orderStatus(order.statusId)
                        .asFlow()
                        .mapToOne(Dispatchers.IO),
                    orderItemQueries
                        .orderItems(order.id)
                        .asFlow()
                        .mapToList(Dispatchers.IO)
                ) { status, items ->
                    OrderWithItems(
                        id = order.id,
                        createdAt = order.createdAt,
                        total = order.total,
                        address = order.address,
                        statusId = order.statusId,
                        completed = order.completed,
                        status = status,
                        items = items,
                    )
                }
            }

    override suspend fun deleteInactiveOrders() =
        withContext(Dispatchers.IO) {
            orderQueries.deleteInactiveOrders()
        }

    override suspend fun insertOrders(orders: List<OrderDetails>) =
        withContext(Dispatchers.IO) {
            orderQueries.transaction {
                orders.forEach {
                    orderQueries.insert(it)
                }
            }
        }

    override suspend fun insertOrderItems(orderItems: List<OrderItem>) =
        withContext(Dispatchers.IO) {
            orderItemQueries.transaction {
                orderItems.forEach {
                    orderItemQueries.insert(it)
                }
            }
        }

    override suspend fun insertOrderStatuses(orderStatuses: List<OrderStatus>) =
        withContext(Dispatchers.IO) {
            orderStatusQueries.transaction {
                orderStatuses.forEach {
                    orderStatusQueries.insert(it)
                }
            }
        }

    override suspend fun deleteOrders() =
        withContext(Dispatchers.IO) {
            orderQueries.deleteAll()
        }
}
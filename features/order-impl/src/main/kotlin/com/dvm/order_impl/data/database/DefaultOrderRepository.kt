package com.dvm.order_impl.data.database

import com.dvm.order_api.domain.model.Order
import com.dvm.order_api.domain.model.OrderDetails
import com.dvm.order_api.domain.model.OrderItem
import com.dvm.order_api.domain.model.OrderStatus
import com.dvm.order_api.domain.model.OrderWithItems
import com.dvm.order_impl.data.mappers.toOrderDetailsEntity
import com.dvm.order_impl.data.mappers.toOrderItem
import com.dvm.order_impl.data.mappers.toOrderItemEntity
import com.dvm.order_impl.data.mappers.toOrderStatus
import com.dvm.order_impl.data.mappers.toOrderStatusEntity
import com.dvm.order_impl.domain.OrderRepository
import com.dvm.orderdatabase.OrderDetailsQueries
import com.dvm.orderdatabase.OrderItemQueries
import com.dvm.orderdatabase.OrderStatusQueries
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOne
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.withContext
import java.util.*

class DefaultOrderRepository(
    private val orderQueries: OrderDetailsQueries,
    private val orderItemQueries: OrderItemQueries,
    private val orderStatusQueries: OrderStatusQueries,
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

    override fun order(orderId: String): Flow<OrderWithItems> =
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
                        status = status.toOrderStatus(),
                        items = items.map { it.toOrderItem() },
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
                    orderQueries.insert(it.toOrderDetailsEntity())
                }
            }
        }

    override suspend fun insertOrderItems(orderItems: List<OrderItem>) =
        withContext(Dispatchers.IO) {
            orderItemQueries.transaction {
                orderItems.forEach {
                    orderItemQueries.insert(it.toOrderItemEntity())
                }
            }
        }

    override suspend fun insertOrderStatuses(orderStatuses: List<OrderStatus>) =
        withContext(Dispatchers.IO) {
            orderStatusQueries.transaction {
                orderStatuses.forEach {
                    orderStatusQueries.insert(it.toOrderStatusEntity())
                }
            }
        }

    override suspend fun deleteOrders() =
        withContext(Dispatchers.IO) {
            orderQueries.deleteAll()
        }
}
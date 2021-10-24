package com.dvm.database.impl.repositories

import com.dvm.database.api.OrderRepository
import com.dvm.database.api.models.Order
import com.dvm.database.api.models.OrderData
import com.dvm.database.api.models.OrderItem
import com.dvm.database.api.models.OrderStatus
import com.dvm.database.api.models.OrderWithItems
import com.dvm.database.impl.dao.OrderDao
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

internal class DefaultOrderRepository @Inject constructor(
    private val orderDao: OrderDao
) : OrderRepository {

    override fun activeOrders(): Flow<List<OrderData>> = orderDao.activeOrders()

    override fun completedOrders(): Flow<List<OrderData>> = orderDao.completedOrders()

    override fun order(orderId: String): Flow<OrderWithItems> = orderDao.order(orderId)

    override suspend fun deleteInactiveOrders() =
        withContext(Dispatchers.IO) {
            orderDao.deleteInactiveOrders()
        }

    override suspend fun insertOrders(orders: List<Order>) =
        withContext(Dispatchers.IO) {
            orderDao.insertOrders(orders)
        }

    override suspend fun insertOrderItems(orderItems: List<OrderItem>) =
        withContext(Dispatchers.IO) {
            orderDao.insertOrderItems(orderItems)
        }

    override suspend fun insertOrderStatuses(orderStatuses: List<OrderStatus>) =
        withContext(Dispatchers.IO) {
            orderDao.insertOrderStatuses(orderStatuses)
        }

    override suspend fun deleteOrders() =
        withContext(Dispatchers.IO) {
            orderDao.deleteOrders()
        }
}
package com.dvm.order_impl.domain

import com.dvm.cart_api.domain.CartInteractor
import com.dvm.order_api.domain.OrderInteractor
import com.dvm.order_api.domain.model.Order
import com.dvm.order_api.domain.model.OrderDetails
import com.dvm.order_api.domain.model.OrderId
import com.dvm.order_api.domain.model.OrderItem
import com.dvm.order_api.domain.model.OrderStatus
import com.dvm.order_api.domain.model.OrderWithItems
import com.dvm.preferences.api.DatastoreRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow

internal class DefaultOrderInteractor(
    private val cartInteractor: CartInteractor,
    private val repository: OrderRepository,
    private val api: OrderApi,
    private val datastore: DatastoreRepository
) : OrderInteractor {
    override fun activeOrders(): Flow<List<Order>> =
        repository.activeOrders()

    override fun completedOrders(): Flow<List<Order>> =
        repository.completedOrders()

    override fun order(orderId: String): Flow<OrderWithItems> =
        repository.order(orderId)

    override suspend fun updateOrders() = coroutineScope {
        val token = datastore.getAccessToken() ?: return@coroutineScope
        val lastUpdateTime = datastore.getLastUpdateTime()

        val statuses = async { api.getStatuses(lastUpdateTime) }
        val orders = api.getOrders(token, lastUpdateTime)

        with(repository) {
            deleteInactiveOrders()
            insertOrders(orders)
            insertOrderItems(
                orders.flatMap { it.items }
            )
            insertOrderStatuses(statuses.await())
        }
    }

    override suspend fun createOrder(
        address: String,
        entrance: Int?,
        floor: Int?,
        apartment: String?,
        intercom: String?,
        comment: String?
    ): OrderId {
        val order = api.createOrder(
            token = requireNotNull(datastore.getAccessToken()),
            address = address,
            entrance = entrance,
            floor = floor,
            apartment = apartment,
            intercom = intercom,
            comment = comment,
        )

        with(repository) {
            insertOrders(listOf(order))
            insertOrderItems(order.items)
            insertOrderStatuses(
                api.getStatuses(datastore.getLastUpdateTime())
            )
        }

        cartInteractor.clearCart()

        return OrderId(order.id)
    }

    override suspend fun cancelOrder(orderId: String) {
        val order = api.cancelOrder(
            token = requireNotNull(datastore.getAccessToken()),
            orderId = orderId
        )
        repository.insertOrders(
            listOf(order)
        )
    }

    override suspend fun deleteInactiveOrders() {
        repository.deleteInactiveOrders()
    }

    override suspend fun insertOrders(orders: List<OrderDetails>) {
        repository.insertOrders(orders)
    }

    override suspend fun insertOrderItems(orderItems: List<OrderItem>) {
        repository.insertOrderItems(orderItems)
    }

    override suspend fun insertOrderStatuses(orderStatuses: List<OrderStatus>) {
        repository.insertOrderStatuses(orderStatuses)
    }

    override suspend fun deleteOrders() {
        repository.deleteOrders()
    }
}
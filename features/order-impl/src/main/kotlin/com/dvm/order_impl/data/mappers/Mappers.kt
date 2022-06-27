package com.dvm.order_impl.data.mappers

import com.dvm.order_api.domain.model.OrderDetails
import com.dvm.order_api.domain.model.OrderItem
import com.dvm.order_api.domain.model.OrderStatus
import com.dvm.order_impl.data.network.response.OrderItemResponse
import com.dvm.order_impl.data.network.response.OrderResponse
import com.dvm.order_impl.data.network.response.StatusResponse
import com.dvm.orderdatabase.OrderDetailsEntity
import com.dvm.orderdatabase.OrderItemEntity
import com.dvm.orderdatabase.OrderStatusEntity
import java.util.*

internal fun OrderDetails.toOrderDetailsEntity() =
    OrderDetailsEntity(
        id = id,
        total = total,
        address = address,
        statusId = statusId,
        active = active,
        completed = completed,
        createdAt = createdAt,
    )

internal fun OrderItem.toOrderItemEntity() =
    OrderItemEntity(
        name = name,
        orderId = orderId,
        image = image,
        amount = amount,
        price = price,
        dishId = dishId,
    )

internal fun OrderItemEntity.toOrderItem() =
    OrderItem(
        name = name,
        orderId = orderId,
        image = image,
        amount = amount,
        price = price,
        dishId = dishId,
    )

internal fun OrderStatus.toOrderStatusEntity() =
    OrderStatusEntity(
        id = id,
        name = name,
        cancelable = cancelable,
        active = active,
    )

internal fun OrderStatusEntity.toOrderStatus() =
    OrderStatus(
        id = id,
        name = name,
        cancelable = cancelable,
        active = active,
    )

internal fun StatusResponse.toOrderStatus() =
    OrderStatus(
        id = id,
        name = name,
        cancelable = cancelable,
        active = active,
    )

internal fun OrderResponse.toOrderDetails() =
    OrderDetails(
        id = id,
        total = total,
        address = address,
        statusId = statusId,
        active = active,
        completed = completed,
        createdAt = Date(createdAt),
        items = items.map { it.toOrderItem(id) }
    )

internal fun OrderItemResponse.toOrderItem(orderId: String) =
    OrderItem(
        name = name,
        orderId = orderId,
        image = image,
        amount = amount,
        price = price,
        dishId = dishId,
    )
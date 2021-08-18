package com.dvm.db.api.mappers

import com.dvm.db.api.models.*
import com.dvm.network.api.response.*
import java.util.*

fun CategoryResponse.toDbEntity() =
    Category(
        id = id,
        name = name,
        order = order,
        parent = parent,
        icon = icon,
        active = active
    )

fun DishResponse.toDbEntity() =
    Dish(
        id = id,
        name = name,
        description = description,
        image = image,
        oldPrice = oldPrice,
        price = price,
        rating = rating,
        commentsCount = commentsCount,
        likes = likes,
        category = category
    )

fun ReviewResponse.toDbEntity() =
    Review(
        dishId = dishId,
        author = author,
        date = date,
        rating = rating,
        text = text,
        active = active
    )

fun ProfileResponse.toDbEntity() =
    Profile(
        firstName = firstName,
        lastName = lastName,
        email = email
    )

fun OrderResponse.toDbEntity() =
    Order(
        id = id,
        total = total,
        address = address,
        statusId = statusId,
        active = active,
        completed = completed,
        createdAt = Date(createdAt)
    )

fun OrderItemResponse.toDbEntity(orderId: String) =
    OrderItem(
        name = name,
        orderId = orderId,
        image = image,
        amount = amount,
        price = price,
        dishId = dishId,
    )

fun StatusResponse.toDbEntity() =
    OrderStatus(
        id = id,
        name = name,
        cancelable = cancelable,
        active = active
    )
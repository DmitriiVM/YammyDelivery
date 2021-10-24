package com.dvm.database.api.mappers

import com.dvm.database.api.models.Category
import com.dvm.database.api.models.Dish
import com.dvm.database.api.models.Order
import com.dvm.database.api.models.OrderItem
import com.dvm.database.api.models.OrderStatus
import com.dvm.database.api.models.Profile
import com.dvm.database.api.models.Review
import com.dvm.network.api.response.CategoryResponse
import com.dvm.network.api.response.DishResponse
import com.dvm.network.api.response.OrderItemResponse
import com.dvm.network.api.response.OrderResponse
import com.dvm.network.api.response.ProfileResponse
import com.dvm.network.api.response.ReviewResponse
import com.dvm.network.api.response.StatusResponse
import java.util.Date

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
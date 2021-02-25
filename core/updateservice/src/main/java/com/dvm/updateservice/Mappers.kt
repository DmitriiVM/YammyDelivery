package com.dvm.updateservice

import com.dvm.db.entities.Category
import com.dvm.db.entities.Dish
import com.dvm.db.entities.Review
import com.dvm.network.response.CategoryResponse
import com.dvm.network.response.DishResponse
import com.dvm.network.response.ReviewResponse

fun CategoryResponse.toDbEntity() =
    Category(
        id = id,
        name = name,
        order = order,
        parent = parent,
        icon = icon,
        active = active,
        createdAt = createdAt,
        updatedAt = updatedAt
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
        category = category,
        active = active,
        createdAt = createdAt,
        updatedAt = updatedAt
    )

fun ReviewResponse.toDbEntity() =
    Review(
        dishId = dishId,
        author = author,
        date = date,
        rating = rating,
        text = text,
        active = active,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
package com.dvm.menu_impl.data.mappers

import com.dvm.menu_api.domain.model.Category
import com.dvm.menu_api.domain.model.Dish
import com.dvm.menu_api.domain.model.Favorite
import com.dvm.menu_api.domain.model.Hint
import com.dvm.menu_api.domain.model.ParentCategory
import com.dvm.menu_api.domain.model.Review
import com.dvm.menu_api.domain.model.Subcategory
import com.dvm.menu_impl.data.network.response.CategoryResponse
import com.dvm.menu_impl.data.network.response.DishResponse
import com.dvm.menu_impl.data.network.response.FavoriteResponse
import com.dvm.menu_impl.data.network.response.ReviewResponse
import com.dvm.menudatabase.CategoryEntity
import com.dvm.menudatabase.DishEntity
import com.dvm.menudatabase.HintEntity
import com.dvm.menudatabase.ParentCategoryEntity
import com.dvm.menudatabase.ReviewEntity
import com.dvm.menudatabase.SubcategoryEntity

internal fun ParentCategoryEntity.toParentCategory() =
    ParentCategory(
        id = id,
        name = name,
        icon = icon
    )

internal fun SubcategoryEntity.toSubcategory() =
    Subcategory(
        id = id,
        name = name,
        parent = parent
    )

internal fun Category.toCategoryEntity() =
    CategoryEntity(
        id = id,
        name = name,
        categoryOrder = categoryOrder,
        parent = parent,
        icon = icon,
        active = active
    )

internal fun Hint.toHintEntity() =
    HintEntity(
        query = query,
        date = date
    )

internal fun Dish.toDishEntity() =
    DishEntity(
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

internal fun CategoryResponse.toCategory() =
    Category(
        id = id,
        name = name,
        categoryOrder = order,
        parent = parent,
        icon = icon,
        active = active,
    )

internal fun DishResponse.toDish() =
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
        isFavorite = false,
    )

internal fun FavoriteResponse.toFavorite() =
    Favorite(
        dishId = dishId,
        favorite = favorite
    )

internal fun ReviewResponse.toReview() =
    Review(
        dishId = dishId,
        author = author,
        date = date,
        rating = rating,
        text = text,
        active = active
    )

internal fun ReviewEntity.toReview() =
    Review(
        dishId = dishId,
        author = author,
        date = date,
        rating = rating,
        text = text,
        active = active ?: false
    )
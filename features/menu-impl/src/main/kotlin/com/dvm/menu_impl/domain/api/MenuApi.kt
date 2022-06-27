package com.dvm.menu_impl.domain.api

import com.dvm.menu_api.domain.model.Category
import com.dvm.menu_api.domain.model.Dish
import com.dvm.menu_api.domain.model.Favorite
import com.dvm.menu_api.domain.model.Review

internal interface MenuApi {

    suspend fun getRecommended(): List<String>

    suspend fun getCategories(
        lastUpdateTime: Long?
    ): List<Category>

    suspend fun getDishes(
        lastUpdateTime: Long?
    ): List<Dish>

    suspend fun getFavorite(
        token: String,
        lastUpdateTime: Long?
    ): List<Favorite>

    suspend fun changeFavorite(
        token: String,
        favorites: Map<String, Boolean>
    )

    suspend fun getReviews(
        dishId: String,
        lastUpdateTime: Long?
    ): List<Review>

    suspend fun addReview(
        token: String,
        dishId: String,
        rating: Int,
        text: String
    )
}
package com.dvm.network.api

import com.dvm.network.api.response.CategoryResponse
import com.dvm.network.api.response.DishResponse
import com.dvm.network.api.response.FavoriteResponse
import com.dvm.network.api.response.ReviewResponse

interface MenuApi {

    suspend fun getRecommended(): List<String>

    suspend fun getCategories(
        lastUpdateTime: Long?,
        limit: Int? = 500
    ): List<CategoryResponse>

    suspend fun getDishes(
        lastUpdateTime: Long?,
        limit: Int? = 500
    ): List<DishResponse>

    suspend fun getFavorite(
        token: String,
        lastUpdateTime: Long?,
        limit: Int? = 500
    ): List<FavoriteResponse>

    suspend fun changeFavorite(
        token: String,
        favorites: Map<String, Boolean>
    )

    suspend fun getReviews(
        dishId: String,
        lastUpdateTime: Long?,
        limit: Int? = 500
    ): List<ReviewResponse>

    suspend fun addReview(
        token: String,
        dishId: String,
        rating: Int,
        text: String
    )
}
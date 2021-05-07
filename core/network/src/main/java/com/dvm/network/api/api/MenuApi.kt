package com.dvm.network.api.api

import com.dvm.network.api.response.CategoryResponse
import com.dvm.network.api.response.DishResponse
import com.dvm.network.api.response.FavoriteResponse
import com.dvm.network.api.response.ReviewResponse

interface MenuApi {

    suspend fun getRecommended(): List<String>

    suspend fun getCategories(limit: Int? = 500): List<CategoryResponse>

    suspend fun getDishes(limit: Int? = 500): List<DishResponse>

    suspend fun getFavorite(limit: Int? = 500): List<FavoriteResponse>

    suspend fun changeFavorite(favorites: Map<String, Boolean>)

    suspend fun getReviews(
        dishId: String,
        limit: Int? = 500
    ): List<ReviewResponse>

    suspend fun addReview(
        dishId: String,
        rating: Int,
        text: String
    )
}
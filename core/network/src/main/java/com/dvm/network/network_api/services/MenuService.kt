package com.dvm.network.network_api.services

import com.dvm.network.network_api.response.CategoryResponse
import com.dvm.network.network_api.response.DishResponse
import com.dvm.network.network_api.response.FavoriteResponse
import com.dvm.network.network_api.response.ReviewResponse

interface MenuService {

    suspend fun getRecommended(): List<String>

    suspend fun getCategories(): List<CategoryResponse>

    suspend fun getDishes(): List<DishResponse>

    suspend fun getFavorite(): FavoriteResponse

    suspend fun changeFavorite(
        dishId: Int,
        favorite: Boolean
    ): FavoriteResponse

    suspend fun getReviews(dishId: String): List<ReviewResponse>

    suspend fun addReview(
        dishId: String,
        rating: Int,
        text: String
    )
}
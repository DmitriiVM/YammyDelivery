package com.dvm.network.impl.api

import com.dvm.network.api.MenuApi
import com.dvm.network.api.response.CategoryResponse
import com.dvm.network.api.response.DishResponse
import com.dvm.network.api.response.FavoriteResponse
import com.dvm.network.api.response.ReviewResponse
import com.dvm.network.impl.ApiService
import com.dvm.network.impl.getAllChunks
import com.dvm.network.impl.request.AddReviewRequest
import com.dvm.network.impl.request.ChangeFavoriteRequest
import javax.inject.Inject

internal class DefaultMenuApi @Inject constructor(
    private val apiService: ApiService
) : MenuApi {

    override suspend fun getRecommended(): List<String> = apiService.getRecommended()

    override suspend fun getCategories(
        lastUpdateTime: Long?
    ): List<CategoryResponse> = getAllChunks { offset, limit ->
        apiService.getCategories(
            ifModifiedSince = lastUpdateTime,
            offset = offset,
            limit = limit
        )
    }

    override suspend fun getDishes(
        lastUpdateTime: Long?
    ): List<DishResponse> = getAllChunks { offset, limit ->
        apiService.getDishes(
            ifModifiedSince = lastUpdateTime,
            offset = offset,
            limit = limit
        )
    }

    override suspend fun getFavorite(
        token: String,
        lastUpdateTime: Long?
    ): List<FavoriteResponse> = getAllChunks { offset, limit ->
        apiService.getFavorite(
            token = token,
            ifModifiedSince = lastUpdateTime,
            offset = offset,
            limit = limit
        )
    }

    override suspend fun changeFavorite(
        token: String,
        favorites: Map<String, Boolean>
    ) {
        apiService.changeFavorite(
            token = token,
            changeFavoriteRequest = favorites.map { favorite ->
                ChangeFavoriteRequest(
                    dishId = favorite.key,
                    favorite = favorite.value
                )
            }
        )
    }

    override suspend fun getReviews(
        dishId: String,
        lastUpdateTime: Long?
    ): List<ReviewResponse> = getAllChunks { offset, limit ->
        apiService.getReviews(
            ifModifiedSince = lastUpdateTime,
            dishId = dishId,
            offset = offset,
            limit = limit
        )
    }

    override suspend fun addReview(
        token: String,
        dishId: String,
        rating: Int,
        text: String
    ) {
        apiService.addReview(
            token = token,
            dishId = dishId,
            addReviewRequest = AddReviewRequest(
                rating = rating,
                text = text
            )
        )
    }
}
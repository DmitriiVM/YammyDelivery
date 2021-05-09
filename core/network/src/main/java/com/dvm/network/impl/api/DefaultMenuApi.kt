package com.dvm.network.impl.api

import com.dvm.network.api.MenuApi
import com.dvm.network.api.response.CategoryResponse
import com.dvm.network.api.response.DishResponse
import com.dvm.network.api.response.FavoriteResponse
import com.dvm.network.api.response.ReviewResponse
import com.dvm.network.impl.ApiService
import com.dvm.network.impl.request.AddReviewRequest
import com.dvm.network.impl.request.ChangeFavoriteRequest
import javax.inject.Inject

internal class DefaultMenuApi @Inject constructor(
    private val apiService: ApiService
) : MenuApi {

    override suspend fun getRecommended(): List<String> = apiService.getRecommended()

    override suspend fun getCategories(
        lastUpdateTime: Long?,
        limit: Int?
    ): List<CategoryResponse> =
        apiService.getCategories(
            ifModifiedSince = lastUpdateTime,
            limit = limit
        )

    override suspend fun getDishes(
        lastUpdateTime: Long?,
        limit: Int?
    ): List<DishResponse> =
        apiService.getDishes(
            ifModifiedSince = lastUpdateTime,
            limit = limit
        )

    override suspend fun getFavorite(
        token: String,
        lastUpdateTime: Long?,
        limit: Int?
    ): List<FavoriteResponse> =
        apiService.getFavorite(
            token = token,
            ifModifiedSince = lastUpdateTime,
            limit = limit
        )


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
        lastUpdateTime: Long?,
        limit: Int?
    ): List<ReviewResponse> =
        apiService.getReviews(
            ifModifiedSince = lastUpdateTime,
            dishId = dishId,
            limit = limit
        )

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
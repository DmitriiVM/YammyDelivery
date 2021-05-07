package com.dvm.network.impl.api

import com.dvm.network.api.api.MenuApi
import com.dvm.network.api.response.CategoryResponse
import com.dvm.network.api.response.DishResponse
import com.dvm.network.api.response.FavoriteResponse
import com.dvm.network.api.response.ReviewResponse
import com.dvm.network.impl.ApiService
import com.dvm.network.impl.request.AddReviewRequest
import com.dvm.network.impl.request.ChangeFavoriteRequest
import com.dvm.preferences.api.data.DatastoreRepository
import javax.inject.Inject

internal class DefaultMenuApi @Inject constructor(
    private val apiService: ApiService,
    private val datastore: DatastoreRepository
) : MenuApi {

    override suspend fun getRecommended(): List<String> = apiService.getRecommended()

    override suspend fun getCategories(limit: Int?): List<CategoryResponse> =
        apiService.getCategories(
            ifModifiedSince = datastore.getLastUpdateTime(),
            limit = limit
        )

    override suspend fun getDishes(limit: Int?): List<DishResponse> =
        apiService.getDishes(
            ifModifiedSince = datastore.getLastUpdateTime(),
            limit = limit
        )

    override suspend fun getFavorite(limit: Int?): List<FavoriteResponse> =
        apiService.getFavorite(
            token = getAccessToken(),
            ifModifiedSince = datastore.getLastUpdateTime(),
            limit = limit
        )


    override suspend fun changeFavorite(favorites: Map<String, Boolean>) {
        apiService.changeFavorite(
            token = getAccessToken(),
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
        limit: Int?
    ): List<ReviewResponse> =
        apiService.getReviews(
            ifModifiedSince = datastore.getLastUpdateTime(),
            dishId = dishId,
            limit = limit
        )

    override suspend fun addReview(
        dishId: String,
        rating: Int,
        text: String
    ) {
        apiService.addReview(
            token = getAccessToken(),
            dishId = dishId,
            addReviewRequest = AddReviewRequest(
                rating = rating,
                text = text
            )
        )
    }

    private suspend fun getAccessToken() = requireNotNull(datastore.getAccessToken())
}
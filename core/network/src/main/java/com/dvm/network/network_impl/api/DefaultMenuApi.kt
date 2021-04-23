package com.dvm.network.network_impl.api

import com.dvm.network.network_api.api.MenuApi
import com.dvm.network.network_api.response.CategoryResponse
import com.dvm.network.network_api.response.DishResponse
import com.dvm.network.network_api.response.FavoriteResponse
import com.dvm.network.network_api.response.ReviewResponse
import com.dvm.network.network_impl.ApiService
import com.dvm.network.network_impl.api
import com.dvm.network.network_impl.request.AddReviewRequest
import com.dvm.network.network_impl.request.ChangeFavoriteRequest
import com.dvm.preferences.datastore_api.data.DatastoreRepository
import javax.inject.Inject

internal class DefaultMenuApi @Inject constructor(
    private val apiService: ApiService,
    private val datastore: DatastoreRepository
) : MenuApi {

    override suspend fun getRecommended(): List<String> =
        api {
            apiService.getRecommended()
        }

    override suspend fun getCategories(): List<CategoryResponse> =
        api {
            apiService.getCategories(limit = 1000)  // TODO temp
        }

    override suspend fun getDishes(): List<DishResponse> =
        api {
            apiService.getDishes(limit = 1000)  // TODO temp
        }

    override suspend fun getFavorite(): FavoriteResponse =
        api {
            apiService.getFavorite(getAccessToken())
        }

    override suspend fun toggleFavorite(
        dishId: String,
        favorite: Boolean
    ) =
        api {
            apiService.changeFavorite(
                token = getAccessToken(),
                changeFavoriteRequest = listOf(
                    ChangeFavoriteRequest(
                        dishId = dishId,
                        favorite = favorite
                    )
                )
            )
        }

    override suspend fun getReviews(dishId: String): List<ReviewResponse> =
        api {
            apiService.getReviews(dishId = dishId)
        }

    override suspend fun addReview(
        dishId: String,
        rating: Int,
        text: String
    ) =
        api {
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
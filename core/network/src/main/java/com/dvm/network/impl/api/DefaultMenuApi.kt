package com.dvm.network.impl.api

import com.dvm.network.api.api.MenuApi
import com.dvm.network.api.response.CategoryResponse
import com.dvm.network.api.response.DishResponse
import com.dvm.network.api.response.FavoriteResponse
import com.dvm.network.api.response.ReviewResponse
import com.dvm.network.impl.ApiService
import com.dvm.network.impl.api
import com.dvm.network.impl.request.AddReviewRequest
import com.dvm.network.impl.request.ChangeFavoriteRequest
import com.dvm.preferences.api.data.DatastoreRepository
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

    override suspend fun getFavorite(): List<FavoriteResponse> =
        api {
            apiService.getFavorite(getAccessToken())
        }

    override suspend fun changeFavorite(
        favorites: Map<String, Boolean>
    ) =
        api {
            apiService.changeFavorite(
                token = getAccessToken(),
                changeFavoriteRequest = favorites.map {
                    ChangeFavoriteRequest(
                        dishId = it.key,
                        favorite = it.value
                    )
                }
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
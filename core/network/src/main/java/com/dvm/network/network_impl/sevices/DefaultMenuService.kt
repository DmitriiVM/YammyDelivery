package com.dvm.network.network_impl.sevices

import com.dvm.network.network_api.response.CategoryResponse
import com.dvm.network.network_api.response.DishResponse
import com.dvm.network.network_api.response.FavoriteResponse
import com.dvm.network.network_api.response.ReviewResponse
import com.dvm.network.network_api.services.MenuService
import com.dvm.network.network_impl.ApiService
import com.dvm.network.network_impl.api
import com.dvm.network.network_impl.request.AddReviewRequest
import com.dvm.network.network_impl.request.ChangeFavoriteRequest
import com.dvm.preferences.datastore_api.data.DatastoreRepository
import javax.inject.Inject

internal class DefaultMenuService @Inject constructor(
    private val apiService: ApiService,
    private val datastore: DatastoreRepository
) : MenuService {

    override suspend fun getRecommended(): List<String> =
        api {
            apiService.getRecommended()
        }

    override suspend fun getCategories(): List<CategoryResponse> =
        api {
        apiService.getCategories()
    }

    override suspend fun getDishes(): List<DishResponse> =
        api {
            apiService.getDishes()
        }

    override suspend fun getFavorite(): FavoriteResponse =
        api {
            apiService.getFavorite(getAccessToken())
        }

    override suspend fun changeFavorite(
        dishId: Int,
        favorite: Boolean
    ): FavoriteResponse =
        api {
            apiService.changeFavorite(
                token = getAccessToken(),
                changeFavoriteRequest = ChangeFavoriteRequest(
                    dishId = dishId,
                    favorite = favorite
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
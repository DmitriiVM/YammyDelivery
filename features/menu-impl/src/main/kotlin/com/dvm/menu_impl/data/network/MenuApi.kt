package com.dvm.menu_impl.data.network

import com.dvm.menu_api.domain.model.Category
import com.dvm.menu_api.domain.model.Dish
import com.dvm.menu_api.domain.model.Favorite
import com.dvm.menu_api.domain.model.Review
import com.dvm.menu_impl.data.mappers.toCategory
import com.dvm.menu_impl.data.mappers.toDish
import com.dvm.menu_impl.data.mappers.toFavorite
import com.dvm.menu_impl.data.mappers.toReview
import com.dvm.menu_impl.data.network.request.AddReviewRequest
import com.dvm.menu_impl.data.network.request.ChangeFavoriteRequest
import com.dvm.menu_impl.data.network.response.CategoryResponse
import com.dvm.menu_impl.data.network.response.DishResponse
import com.dvm.menu_impl.data.network.response.FavoriteResponse
import com.dvm.menu_impl.data.network.response.ReviewResponse
import com.dvm.menu_impl.domain.api.MenuApi
import com.dvm.network.util.getAllChunks
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

internal class DefaultMenuApi(
    private val client: HttpClient
) : MenuApi {

    override suspend fun getRecommended(): List<String> =
        client.get("main/recommend").body()

    override suspend fun getCategories(
        lastUpdateTime: Long?
    ): List<Category> = getAllChunks { offset, limit ->
        client.get("categories") {
            header(HttpHeaders.IfModifiedSince, lastUpdateTime)
            parameter(HEADER_OFFSET, offset)
            parameter(HEADER_LIMIT, limit)
        }
            .body<List<CategoryResponse>>()
            .map { it.toCategory() }
    }

    override suspend fun getDishes(
        lastUpdateTime: Long?
    ): List<Dish> = getAllChunks { offset, limit ->
        client.get("dishes") {
            header(HttpHeaders.IfModifiedSince, lastUpdateTime)
            parameter(HEADER_OFFSET, offset)
            parameter(HEADER_LIMIT, limit)
        }
            .body<List<DishResponse>>()
            .map { it.toDish() }
    }

    override suspend fun getFavorite(
        token: String,
        lastUpdateTime: Long?
    ): List<Favorite> = getAllChunks { offset, limit ->
        client.get("favorite") {
            header(HttpHeaders.Authorization, token)
            header(HttpHeaders.IfModifiedSince, lastUpdateTime)
            parameter(HEADER_OFFSET, offset)
            parameter(HEADER_LIMIT, limit)
        }
            .body<List<FavoriteResponse>>()
            .map { it.toFavorite() }
    }

    override suspend fun changeFavorite(
        token: String,
        favorites: Map<String, Boolean>
    ) {
        client.put("favorite/change") {
            header(HttpHeaders.Authorization, token)
            setBody(
                favorites.map { favorite ->
                    ChangeFavoriteRequest(
                        dishId = favorite.key,
                        favorite = favorite.value
                    )
                }
            )
        }
    }

    override suspend fun getReviews(
        dishId: String,
        lastUpdateTime: Long?
    ): List<Review> = getAllChunks { offset, limit ->
        client.get("reviews/$dishId") {
            header(HttpHeaders.IfModifiedSince, lastUpdateTime)
            parameter(HEADER_OFFSET, offset)
            parameter(HEADER_LIMIT, limit)
        }
            .body<List<ReviewResponse>>()
            .map { it.toReview() }
    }

    override suspend fun addReview(
        token: String,
        dishId: String,
        rating: Int,
        text: String
    ) {
        client.post("reviews/$dishId") {
            header(HttpHeaders.Authorization, token)
            setBody(
                AddReviewRequest(
                    rating = rating,
                    text = text
                )
            )
        }
    }

    companion object {
        private const val HEADER_OFFSET = "offset"
        private const val HEADER_LIMIT = "limit"
    }
}
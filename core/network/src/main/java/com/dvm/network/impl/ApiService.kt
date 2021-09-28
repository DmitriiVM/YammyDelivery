package com.dvm.network.impl

import com.dvm.network.api.response.AddressResponse
import com.dvm.network.api.response.AuthResponse
import com.dvm.network.api.response.CartResponse
import com.dvm.network.api.response.CategoryResponse
import com.dvm.network.api.response.DishResponse
import com.dvm.network.api.response.FavoriteResponse
import com.dvm.network.api.response.OrderResponse
import com.dvm.network.api.response.ProfileResponse
import com.dvm.network.api.response.ReviewResponse
import com.dvm.network.api.response.StatusResponse
import com.dvm.network.api.response.TokenResponse
import com.dvm.network.impl.request.AddReviewRequest
import com.dvm.network.impl.request.CancelOrderRequest
import com.dvm.network.impl.request.ChangeFavoriteRequest
import com.dvm.network.impl.request.ChangePasswordRequest
import com.dvm.network.impl.request.CheckCoordinatesRequest
import com.dvm.network.impl.request.CheckInputRequest
import com.dvm.network.impl.request.CreateOrderRequest
import com.dvm.network.impl.request.EdieProfileRequest
import com.dvm.network.impl.request.LoginRequest
import com.dvm.network.impl.request.RefreshTokenRequest
import com.dvm.network.impl.request.RegisterRequest
import com.dvm.network.impl.request.ResetPasswordRequest
import com.dvm.network.impl.request.SendCodeRequest
import com.dvm.network.impl.request.SendEmailRequest
import com.dvm.network.impl.request.UpdateCartRequest
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

internal class ApiService(
    private val client: HttpClient
) {

    suspend fun getRecommended(): List<String> =
        client.get("main/recommend")

    suspend fun getCategories(
        ifModifiedSince: Long?,
        offset: Int,
        limit: Int
    ): List<CategoryResponse> =
        client.get("categories") {
            header(HttpHeaders.IfModifiedSince, ifModifiedSince)
            parameter(HEADER_OFFSET, offset)
            parameter(HEADER_LIMIT, limit)
        }

    suspend fun getDishes(
        ifModifiedSince: Long?,
        offset: Int,
        limit: Int
    ): List<DishResponse> =
        client.get("dishes") {
            header(HttpHeaders.IfModifiedSince, ifModifiedSince)
            parameter(HEADER_OFFSET, offset)
            parameter(HEADER_LIMIT, limit)
        }

    suspend fun getReviews(
        dishId: String,
        ifModifiedSince: Long?,
        offset: Int,
        limit: Int
    ): List<ReviewResponse> =
        client.get("reviews/$dishId") {
            header(HttpHeaders.IfModifiedSince, ifModifiedSince)
            parameter(HEADER_OFFSET, offset)
            parameter(HEADER_LIMIT, limit)
        }

    suspend fun addReview(
        dishId: String,
        token: String,
        addReviewRequest: AddReviewRequest
    ): Unit =
        client.post("reviews/$dishId") {
            header(HttpHeaders.Authorization, token)
            body = addReviewRequest
        }

    suspend fun getFavorite(
        token: String,
        ifModifiedSince: Long?,
        offset: Int,
        limit: Int
    ): List<FavoriteResponse> =
        client.get("favorite") {
            header(HttpHeaders.Authorization, token)
            header(HttpHeaders.IfModifiedSince, ifModifiedSince)
            parameter(HEADER_OFFSET, offset)
            parameter(HEADER_LIMIT, limit)
        }

    suspend fun changeFavorite(
        token: String,
        changeFavoriteRequest: List<ChangeFavoriteRequest>
    ): Unit =
        client.put("favorite/change") {
            header(HttpHeaders.Authorization, token)
            body = changeFavoriteRequest
        }

    suspend fun getCart(token: String): CartResponse =
        client.get("cart") {
            header(HttpHeaders.Authorization, token)
        }

    suspend fun updateCart(
        token: String,
        updateCartRequest: UpdateCartRequest
    ): CartResponse =
        client.put("cart") {
            header(HttpHeaders.Authorization, token)
            body = updateCartRequest
        }

    suspend fun checkInput(checkInputRequest: CheckInputRequest): AddressResponse =
        client.post("address/input") {
            body = checkInputRequest
        }

    suspend fun checkCoordinates(
        checkCoordinatesRequest: CheckCoordinatesRequest
    ): AddressResponse =
        client.post("address/coordinates") {
            body = checkCoordinatesRequest
        }

    suspend fun login(loginRequest: LoginRequest): AuthResponse =
        client.post("auth/login") {
            body = loginRequest
        }

    suspend fun register(registerRequest: RegisterRequest): AuthResponse =
        client.post("auth/register") {
            body = registerRequest
        }


    suspend fun sendEmail(sendEmailRequest: SendEmailRequest): Unit =
        client.post("auth/recovery/email") {
            body = sendEmailRequest
        }

    suspend fun sendCode(sendCodeRequest: SendCodeRequest): Unit =
        client.post("auth/recovery/code") {
            body = sendCodeRequest
        }

    suspend fun resetPassword(resetPasswordRequest: ResetPasswordRequest): Unit =
        client.post("auth/recovery/password") {
            body = resetPasswordRequest
        }

    suspend fun refreshToken(refreshTokenRequest: RefreshTokenRequest): TokenResponse =
        client.post("auth/refresh") {
            body = refreshTokenRequest
        }

    suspend fun createOrder(
        token: String,
        createOrderRequest: CreateOrderRequest
    ): OrderResponse =
        client.post("orders/new") {
            header(HttpHeaders.Authorization, token)
            body = createOrderRequest
        }

    suspend fun getOrders(
        token: String,
        ifModifiedSince: Long?,
        offset: Int,
        limit: Int,
    ): List<OrderResponse> =
        client.get("orders") {
            header(HttpHeaders.Authorization, token)
            header(HttpHeaders.IfModifiedSince, ifModifiedSince)
            parameter(HEADER_OFFSET, offset)
            parameter(HEADER_LIMIT, limit)
        }

    suspend fun getStatuses(ifModifiedSince: Long?): List<StatusResponse> =
        client.get("orders/statuses") {
            header(HttpHeaders.IfModifiedSince, ifModifiedSince)
        }

    suspend fun cancelOrder(
        token: String,
        cancelOrderRequest: CancelOrderRequest
    ): OrderResponse =
        client.put("orders/cancel") {
            header(HttpHeaders.Authorization, token)
            body = cancelOrderRequest
        }

    suspend fun getProfile(token: String): ProfileResponse =
        client.get("profile") {
            header(HttpHeaders.Authorization, token)
        }

    suspend fun editProfile(
        token: String,
        editProfileRequest: EdieProfileRequest
    ): ProfileResponse =
        client.put("profile") {
            header(HttpHeaders.Authorization, token)
            body = editProfileRequest
        }

    suspend fun changePassword(
        token: String,
        changePasswordRequest: ChangePasswordRequest
    ): Unit =
        client.put("profile/password") {
            header(HttpHeaders.Authorization, token)
            body = changePasswordRequest
        }

    companion object {
        private const val HEADER_OFFSET = "offset"
        private const val HEADER_LIMIT = "limit"
    }
}
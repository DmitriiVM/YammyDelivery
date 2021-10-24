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
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

internal interface ApiService {

    @GET("main/recommend")
    suspend fun getRecommended(): List<String>

    @GET("categories")
    suspend fun getCategories(
        @Header(HEADER_IF_MODIFIED_SINCE) ifModifiedSince: Long?,
        @Query(HEADER_OFFSET) offset: Int,
        @Query(HEADER_LIMIT) limit: Int
    ): List<CategoryResponse>

    @GET("dishes")
    suspend fun getDishes(
        @Header(HEADER_IF_MODIFIED_SINCE) ifModifiedSince: Long?,
        @Query(HEADER_OFFSET) offset: Int,
        @Query(HEADER_LIMIT) limit: Int
    ): List<DishResponse>

    @GET("reviews/{dishId}")
    suspend fun getReviews(
        @Path("dishId") dishId: String,
        @Header(HEADER_IF_MODIFIED_SINCE) ifModifiedSince: Long?,
        @Query(HEADER_OFFSET) offset: Int,
        @Query(HEADER_LIMIT) limit: Int
    ): List<ReviewResponse>

    @POST("reviews/{dishId}")
    suspend fun addReview(
        @Path("dishId") dishId: String,
        @Header(HEADER_AUTHORIZATION) token: String,
        @Body addReviewRequest: AddReviewRequest
    )

    @GET("favorite")
    suspend fun getFavorite(
        @Header(HEADER_AUTHORIZATION) token: String,
        @Header(HEADER_IF_MODIFIED_SINCE) ifModifiedSince: Long?,
        @Query(HEADER_OFFSET) offset: Int,
        @Query(HEADER_LIMIT) limit: Int
    ): List<FavoriteResponse>

    @PUT("favorite/change")
    suspend fun changeFavorite(
        @Header(HEADER_AUTHORIZATION) token: String,
        @Body changeFavoriteRequest: List<ChangeFavoriteRequest>
    )

    @GET("cart")
    suspend fun getCart(
        @Header(HEADER_AUTHORIZATION) token: String
    ): CartResponse

    @PUT("cart")
    suspend fun updateCart(
        @Header(HEADER_AUTHORIZATION) token: String,
        @Body updateCartRequest: UpdateCartRequest
    ): CartResponse

    @POST("address/input")
    suspend fun checkInput(
        @Body checkInputRequest: CheckInputRequest
    ): AddressResponse

    @POST("address/coordinates")
    suspend fun checkCoordinates(
        @Body checkCoordinatesRequest: CheckCoordinatesRequest
    ): AddressResponse

    @POST("auth/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): AuthResponse

    @POST("auth/register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): AuthResponse

    @POST("auth/recovery/email")
    suspend fun sendEmail(
        @Body sendEmailRequest: SendEmailRequest
    )

    @POST("auth/recovery/code")
    suspend fun sendCode(
        @Body sendCodeRequest: SendCodeRequest
    )

    @POST("auth/recovery/password")
    suspend fun resetPassword(
        @Body resetPasswordRequest: ResetPasswordRequest
    )

    @POST("auth/refresh")
    suspend fun refreshToken(
        @Body refreshTokenRequest: RefreshTokenRequest
    ): TokenResponse

    @POST("orders/new")
    suspend fun createOrder(
        @Header(HEADER_AUTHORIZATION) token: String,
        @Body createOrderRequest: CreateOrderRequest
    ): OrderResponse

    @GET("orders")
    suspend fun getOrders(
        @Header(HEADER_AUTHORIZATION) token: String,
        @Header(HEADER_IF_MODIFIED_SINCE) ifModifiedSince: Long?,
        @Query(HEADER_OFFSET) offset: Int,
        @Query(HEADER_LIMIT) limit: Int,
    ): List<OrderResponse>

    @GET("orders/statuses")
    suspend fun getStatuses(
        @Header(HEADER_IF_MODIFIED_SINCE) ifModifiedSince: Long?,
    ): List<StatusResponse>

    @PUT("orders/cancel")
    suspend fun cancelOrder(
        @Header(HEADER_AUTHORIZATION) token: String,
        @Body cancelOrderRequest: CancelOrderRequest
    ): OrderResponse

    @GET("profile")
    suspend fun getProfile(
        @Header(HEADER_AUTHORIZATION) token: String
    ): ProfileResponse

    @PUT("profile")
    suspend fun editProfile(
        @Header(HEADER_AUTHORIZATION) token: String,
        @Body editProfileRequest: EdieProfileRequest
    ): ProfileResponse

    @PUT("profile/password")
    suspend fun changePassword(
        @Header(HEADER_AUTHORIZATION) token: String,
        @Body changePasswordRequest: ChangePasswordRequest
    )

    companion object {
        const val HEADER_AUTHORIZATION = "Authorization"
        private const val HEADER_IF_MODIFIED_SINCE = "If-Modified-Since"
        private const val HEADER_OFFSET = "offset"
        private const val HEADER_LIMIT = "limit"
    }
}
package com.dvm.network.impl

import com.dvm.network.api.response.*
import com.dvm.network.impl.request.*
import retrofit2.http.*

internal interface ApiService {

    @GET("main/recommend")
    suspend fun getRecommended(): List<String>

    @GET("categories")
    suspend fun getCategories(
        @Header("If-Modified-Since") ifModifiedSince: Long? = 0,
        @Query("offset") offset: Int? = null,
        @Query("limit") limit: Int? = null
    ): List<CategoryResponse>

    @GET("dishes")
    suspend fun getDishes(
        @Header("If-Modified-Since") ifModifiedSince: Long? = 0,
        @Query("offset") offset: Int? = null,
        @Query("limit") limit: Int? = null
    ): List<DishResponse>

    @GET("reviews/{dishId}")
    suspend fun getReviews(
        @Path("dishId") dishId: String,
        @Header("If-Modified-Since") ifModifiedSince: Long? = 0,
        @Query("offset") offset: Int? = null,
        @Query("limit") limit: Int? = null
    ): List<ReviewResponse>

    @POST("reviews/{dishId}")
    suspend fun addReview(
        @Path("dishId") dishId: String,
        @Header("Authorization") token: String,
        @Body addReviewRequest: AddReviewRequest
    )

    @GET("favorite")
    suspend fun getFavorite(
        @Header("Authorization") token: String,
        @Header("If-Modified-Since") ifModifiedSince: Long? = 0,
        @Query("offset") offset: Int? = null,
        @Query("limit") limit: Int? = null
    ): List<FavoriteResponse>

    @PUT("favorite/change")
    suspend fun changeFavorite(
        @Header("Authorization") token: String,
        @Body changeFavoriteRequest: List<ChangeFavoriteRequest>
    )

    @GET("cart")
    suspend fun getCart(
        @Header("Authorization") token: String
    ): CartResponse

    @PUT("cart")
    suspend fun updateCart(
        @Header("Authorization") token: String,
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
        @Body rerTokenRequest: RefreshTokenRequest
    ): TokenResponse

    @POST("orders/new")
    suspend fun createOrder(
        @Header("Authorization") token: String,
        @Body createOrderRequest: CreateOrderRequest
    ): OrderResponse

    @GET("orders")
    suspend fun getOrders(
        @Header("Authorization") token: String,
        @Header("If-Modified-Since") ifModifiedSince: Long? = 0,
        @Query("offset") offset: Int? = null,
        @Query("limit") limit: Int? = null,
    ): List<OrderResponse>

    @GET("orders/statuses")
    suspend fun getStatuses(
        @Header("If-Modified-Since") ifModifiedSince: Long? = 0,
    ): List<StatusResponse>

    @PUT("orders/cancel")
    suspend fun cancelOrder(
        @Header("Authorization") token: String,
        @Body cancelOrderRequest: CancelOrderRequest
    ): OrderResponse

    @GET("profile")
    suspend fun getProfile(
        @Header("Authorization") token: String
    ): ProfileResponse

    @PUT("profile")
    suspend fun editProfile(
        @Header("Authorization") token: String,
        @Body editProfileRequest: EdieProfileRequest
    ): ProfileResponse

    @PUT("profile/password")
    suspend fun changePassword(
        @Header("Authorization") token: String,
        @Body changePasswordRequest: ChangePasswordRequest
    )
}
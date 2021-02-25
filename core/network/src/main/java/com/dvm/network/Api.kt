package com.dvm.network

import com.dvm.network.response.*
import com.google.gson.JsonElement
import retrofit2.http.*

interface Api {

    // main

    @GET("main/recommend")
    suspend fun getRecommended(): List<String>

    @GET("categories")
    suspend fun getCategories(
        @Header("If-Modified-Since") ifModifiedSince: Long? = 0,
        @Query("offset") offset: Int? = null,
        @Query("limit") limit: Int? = 100
    ): List<CategoryResponse>

    @GET("dishes")
    suspend fun getDishes(
        @Header("If-Modified-Since") ifModifiedSince: Long? = 0,
        @Query("offset") offset: Int? = null,
        @Query("limit") limit: Int? = null
    ): List<DishResponse>

    @GET("reviews/{dishId}")
    suspend fun getReviews(
        @Header("If-Modified-Since") ifModifiedSince: Long? = 0,
        @Path("dishId") dishId: String,
        @Query("offset") offset: Int? = null,
        @Query("limit") limit: Int? = null
    ): List<ReviewResponse>

    @FormUrlEncoded
    @POST("reviews/{dishId}")
    suspend fun addReview(
        @Header("Authorization") token: TokenResponse,
        @Path("dishId") dishId: String,
        @Field("rating") rating: Int,
        @Field("text") text: String
    )

    // favorite

    @GET("favorite")
    fun getFavorite(
        @Header("Authorization") token: String,
        @Header("If-Modified-Since") ifModifiedSince: Long? = 0,
        @Query("offset") offset: Int? = null,
        @Query("limit") limit: Int? = null
    ): JsonElement

    @FormUrlEncoded
    @PUT("favorite")
    fun changeFavorite(
        @Header("Authorization") token: TokenResponse,
        @Field("dishId") dishId: Int,
        @Field("favorite") favorite: Boolean
    )


    // cart

    @GET("cart")
    suspend fun getCart(
        @Header("Authorization") token: TokenResponse
    ): CartResponse

    @FormUrlEncoded
    @PUT("cart")
    suspend fun updateCart(
        @Header("Authorization") token: TokenResponse,
//        @Field
//    @Body
    ): CartResponse


    //

    @FormUrlEncoded
    @POST("address/input")
    suspend fun checkInput(
        @Field("address") address: String
    ): AddressResponse

    @FormUrlEncoded
    @POST("address/coordinates")
    suspend fun checkCoordinates(
        @Field("lat") lat: Long,
        @Field("lon") lon: Long
    ): AddressResponse


    // auth

//    @FormUrlEncoded
//    @POST("auth/login")
//    suspend fun login(
//        @Field("email") email: String,
//        @Field("password") password: String
//    ): Login


    @POST("auth/login")
    suspend fun login(
        @Body login: LoginInfo
    ): LoginResponse

//    @Headers("Content-Type: application/json")
//    @FormUrlEncoded
//    @POST("auth/register")
//    suspend fun register(
//        @Field("firstName") firstName: String,
//        @Field("lastName") lastName: String,
//        @Field("email") email: String,
//        @Field("password") password: String
//    ): Login

    //    @Headers("Content-Type: application/json")
    @POST("auth/register")
    suspend fun register(
        @Body info: RegisterInfo
    ): LoginResponse

    // Ответ 200 - код отправлен на почту
    // Ответ 400 - код уже был отправлен менее 3 минут назад
    @FormUrlEncoded
    @POST("auth/recovery/email")
    suspend fun sendEmail(
        @Field("email") email: String
    )

    // Ответ 200 - код верный
    // Ответ 400 - код неверный
    @FormUrlEncoded
    @POST("auth/recovery/code")
    suspend fun sendCode(
        @Field("email") email: String,
        @Field("code") code: String
    )

    // Ответ 200 - пароль изменен
    // Ответ 402 - время действия кода истекло
    @FormUrlEncoded
    @POST("auth/recovery/password")
    suspend fun resetPassword(
        @Field("email") email: String,
        @Field("code") code: String,
        @Field("password") password: String
    )

    @FormUrlEncoded
    @POST("auth/refresh")
    suspend fun refresh(
        @Field("refreshToken") refreshToken: String
    ): TokenResponse


    // order

    @FormUrlEncoded
    @POST("orders/new")
    suspend fun createOrder(
        @Header("Authorization") token: TokenResponse,
        @Field("address") address: String,
        @Field("entrance") entrance: Int,
        @Field("floor") floor: Int,
        @Field("apartment") apartment: String,
        @Field("intercom") intercom: String,
        @Field("comment") comment: String
//    @Body  // TODO
    ): OrderResponse

    @GET("orders")
    suspend fun getOrders(
        @Header("Authorization") token: TokenResponse,
        @Header("If-Modified-Since") ifModifiedSince: Long? = 0,
        @Query("offset") offset: Int? = null,
        @Query("limit") limit: Int? = null,
    ): List<OrderResponse>


    // timeout
    @GET("orders/status")
    suspend fun getStatuses(
        @Header("If-Modified-Since") ifModifiedSince: Long? = 0,
    ): List<StatusResponse>


    @FormUrlEncoded
    @PUT("orders/cancel")
    suspend fun cancelOrder(
        @Header("Authorization") token: TokenResponse,
        @Query("orderId") orderId: Int
    ): OrderResponse


    // profile

    @GET("profile")
    suspend fun getProfile(
        @Header("Authorization") token: TokenResponse
    ): ProfileResponse

    @FormUrlEncoded
    @PUT("profile")
    suspend fun editProfile(
        @Header("Authorization") token: TokenResponse,
        @Field("firstName") firstName: String,
        @Field("lastName") lastName: String,
        @Field("email") email: String
    ): ProfileResponse

    @FormUrlEncoded
    @PUT("profile/password")
    suspend fun changePassword(
        @Header("Authorization") token: TokenResponse,
        @Field("oldPassword") oldPassword: String,
        @Field("newPassword") newPassword: String
    )

}

data class RegisterInfo(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String
)

data class LoginInfo(
    val email: String,
    val password: String
)
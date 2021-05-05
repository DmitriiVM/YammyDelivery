package com.dvm.network.api.api

import com.dvm.network.api.response.AuthResponse
import com.dvm.network.api.response.TokenResponse

interface AuthApi {

    suspend fun login(
        login: String,
        password: String
    ): AuthResponse

    suspend fun register(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ): AuthResponse

    suspend fun sendEmail(email: String)

    suspend fun sendCode(
        email: String,
        code: String
    )

    suspend fun resetPassword(
        email: String,
        code: String,
        password: String
    )

    suspend fun refreshToken(
        refreshToken: String
    ): TokenResponse
}
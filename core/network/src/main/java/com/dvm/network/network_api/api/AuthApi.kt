package com.dvm.network.network_api.api

import com.dvm.network.network_api.response.AuthResponse

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
}
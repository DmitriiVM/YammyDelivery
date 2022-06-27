package com.dvm.auth_impl.domain

import com.dvm.auth_impl.domain.model.AuthData
import com.dvm.auth_impl.domain.model.Token

internal interface AuthApi {

    suspend fun login(
        login: String,
        password: String
    ): AuthData

    suspend fun register(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ): AuthData

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
    ): Token
}
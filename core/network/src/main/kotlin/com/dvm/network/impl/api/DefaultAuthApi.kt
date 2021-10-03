package com.dvm.network.impl.api

import com.dvm.network.api.AuthApi
import com.dvm.network.api.response.AuthResponse
import com.dvm.network.api.response.TokenResponse
import com.dvm.network.impl.ApiService
import com.dvm.network.impl.request.*

internal class DefaultAuthApi(
    private val apiService: ApiService
) : AuthApi {

    override suspend fun login(
        login: String,
        password: String
    ): AuthResponse =
        apiService.login(
            LoginRequest(login, password)
        )

    override suspend fun register(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ): AuthResponse =
        apiService.register(
            RegisterRequest(
                firstName = firstName,
                lastName = lastName,
                email = email,
                password = password
            )
        )

    override suspend fun sendEmail(email: String) {
        apiService.sendEmail(
            SendEmailRequest(email)
        )
    }

    override suspend fun sendCode(email: String, code: String) {
        apiService.sendCode(
            SendCodeRequest(email, code)
        )
    }

    override suspend fun resetPassword(
        email: String,
        code: String,
        password: String
    ) {
        apiService.resetPassword(
            ResetPasswordRequest(
                email = email,
                code = code,
                password = password
            )
        )
    }

    override suspend fun refreshToken(refreshToken: String): TokenResponse =
        apiService.refreshToken(
            RefreshTokenRequest(refreshToken)
        )
}
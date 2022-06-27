package com.dvm.auth_impl.data.network

import com.dvm.auth_impl.data.mappers.toAuthData
import com.dvm.auth_impl.data.mappers.toToken
import com.dvm.auth_impl.data.network.request.LoginRequest
import com.dvm.auth_impl.data.network.request.RegisterRequest
import com.dvm.auth_impl.data.network.request.ResetPasswordRequest
import com.dvm.auth_impl.data.network.request.SendCodeRequest
import com.dvm.auth_impl.data.network.request.SendEmailRequest
import com.dvm.auth_impl.data.network.response.AuthResponse
import com.dvm.auth_impl.domain.AuthApi
import com.dvm.auth_impl.domain.model.AuthData
import com.dvm.auth_impl.domain.model.Token
import com.dvm.network.model.RefreshTokenRequest
import com.dvm.network.model.TokenResponse
import io.ktor.client.*
import io.ktor.client.request.*

internal class DefaultAuthApi(
    private val client: HttpClient
) : AuthApi {

    override suspend fun login(
        login: String,
        password: String
    ): AuthData =
        client.post<AuthResponse>("auth/login") {
            body = LoginRequest(login, password)
        }
            .toAuthData()

    override suspend fun register(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ): AuthData =
        client.post<AuthResponse>("auth/register") {
            body = RegisterRequest(
                firstName = firstName,
                lastName = lastName,
                email = email,
                password = password
            )
        }
            .toAuthData()

    override suspend fun sendEmail(email: String): Unit =
        client.post("auth/recovery/email") {
            body = SendEmailRequest(email)
        }

    override suspend fun sendCode(email: String, code: String): Unit =
        client.post("auth/recovery/code") {
            body = SendCodeRequest(email, code)
        }

    override suspend fun resetPassword(
        email: String,
        code: String,
        password: String
    ): Unit =
        client.post("auth/recovery/password") {
            body = ResetPasswordRequest(
                email = email,
                code = code,
                password = password
            )
        }

    override suspend fun refreshToken(refreshToken: String): Token =
        client.post<TokenResponse>("auth/refresh") {
            body = RefreshTokenRequest(refreshToken)
        }
            .toToken()
}
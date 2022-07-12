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
import io.ktor.client.call.*
import io.ktor.client.request.*

internal class DefaultAuthApi(
    private val client: HttpClient
) : AuthApi {

    override suspend fun login(
        login: String,
        password: String
    ): AuthData =
        client.post("auth/login") {
            setBody(LoginRequest(login, password))
        }
            .body<AuthResponse>()
            .toAuthData()

    override suspend fun register(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ): AuthData =
        client.post("auth/register") {
            setBody(
                RegisterRequest(
                    firstName = firstName,
                    lastName = lastName,
                    email = email,
                    password = password
                )
            )
        }
            .body<AuthResponse>()
            .toAuthData()

    override suspend fun sendEmail(email: String) {
        client.post("auth/recovery/email") {
            setBody(SendEmailRequest(email))
        }
    }

    override suspend fun sendCode(email: String, code: String) {
        client.post("auth/recovery/code") {
            setBody(SendCodeRequest(email, code))
        }
    }


    override suspend fun resetPassword(
        email: String,
        code: String,
        password: String
    ) {
        client.post("auth/recovery/password") {
            setBody(
                ResetPasswordRequest(
                    email = email,
                    code = code,
                    password = password
                )
            )
        }
    }


    override suspend fun refreshToken(refreshToken: String): Token =
        client.post("auth/refresh") {
            setBody(RefreshTokenRequest(refreshToken))
        }
            .body<TokenResponse>()
            .toToken()
}
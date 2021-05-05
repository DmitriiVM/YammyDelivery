package com.dvm.network.impl.api

import com.dvm.network.api.api.AuthApi
import com.dvm.network.api.response.AuthResponse
import com.dvm.network.impl.ApiService
import com.dvm.network.impl.api
import com.dvm.network.impl.request.*
import javax.inject.Inject

internal class DefaultAuthApi @Inject constructor(
    private val apiService: ApiService
) : AuthApi {

    override suspend fun login(
        login: String,
        password: String
    ): AuthResponse =
        api {
            apiService.login(
                LoginRequest(login, password)
            )
        }

    override suspend fun register(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ): AuthResponse =
        api {
            apiService.register(
                RegisterRequest(
                    firstName = firstName,
                    lastName = lastName,
                    email = email,
                    password = password
                )
            )
        }

    override suspend fun sendEmail(email: String) =
        api {
            apiService.sendEmail(
                SendEmailRequest(email)
            )
        }

    override suspend fun sendCode(
        email: String,
        code: String
    ) =
        api {
            apiService.sendCode(
                SendCodeRequest(email, code)
            )
        }

    override suspend fun resetPassword(
        email: String,
        code: String,
        password: String
    ) =
        api {
            apiService.resetPassword(
                ResetPasswordRequest(
                    email = email,
                    code = code,
                    password = password
                )
            )
        }

    override suspend fun refreshToken(refreshToken: String) =
        api {
            apiService.refreshToken(
                RefreshTokenRequest(refreshToken)
            )
        }
}
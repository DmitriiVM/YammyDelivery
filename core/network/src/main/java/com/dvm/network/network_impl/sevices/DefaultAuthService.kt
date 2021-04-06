package com.dvm.network.network_impl.sevices

import com.dvm.network.network_api.response.AuthResponse
import com.dvm.network.network_api.services.AuthService
import com.dvm.network.network_impl.ApiService
import com.dvm.network.network_impl.api
import com.dvm.network.network_impl.request.*
import javax.inject.Inject

internal class DefaultAuthService @Inject constructor(
    private val apiService: ApiService
) : AuthService {

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
}
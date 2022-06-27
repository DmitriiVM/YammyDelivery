package com.dvm.auth_impl.data.mappers

import com.dvm.auth_impl.data.network.response.AuthResponse
import com.dvm.auth_impl.domain.model.AuthData
import com.dvm.auth_impl.domain.model.Token
import com.dvm.network.model.TokenResponse

internal fun AuthResponse.toAuthData() =
    AuthData(
        id = id,
        firstName = firstName,
        lastName = lastName,
        email = email,
        accessToken = accessToken,
        refreshToken = refreshToken
    )

internal fun TokenResponse.toToken() =
    Token(accessToken = accessToken)
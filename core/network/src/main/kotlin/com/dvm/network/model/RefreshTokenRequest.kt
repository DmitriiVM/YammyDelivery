package com.dvm.network.model

import kotlinx.serialization.Serializable

@Serializable
class RefreshTokenRequest(
    val refreshToken: String
)
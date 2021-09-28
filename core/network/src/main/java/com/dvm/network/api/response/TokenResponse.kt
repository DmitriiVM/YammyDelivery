package com.dvm.network.api.response

import kotlinx.serialization.Serializable

@Serializable
data class TokenResponse(
    val accessToken: String
)
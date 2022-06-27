package com.dvm.network.model

import kotlinx.serialization.Serializable

@Serializable
data class TokenResponse(
    val accessToken: String
)
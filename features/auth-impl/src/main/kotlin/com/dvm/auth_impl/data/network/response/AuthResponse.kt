package com.dvm.auth_impl.data.network.response

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val accessToken: String,
    val refreshToken: String
)
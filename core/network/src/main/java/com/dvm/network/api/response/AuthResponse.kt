package com.dvm.network.api.response

data class AuthResponse(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val accessToken: String,
    val refreshToken: String
)
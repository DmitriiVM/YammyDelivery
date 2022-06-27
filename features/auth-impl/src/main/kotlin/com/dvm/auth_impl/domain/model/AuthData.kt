package com.dvm.auth_impl.domain.model

data class AuthData(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val accessToken: String,
    val refreshToken: String
)
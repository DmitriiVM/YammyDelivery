package com.dvm.auth_impl.data.network.request

import kotlinx.serialization.Serializable

@Serializable
internal class RegisterRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String
)
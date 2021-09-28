package com.dvm.network.impl.request

import kotlinx.serialization.Serializable

@Serializable
internal class LoginRequest(
    val email: String,
    val password: String
)
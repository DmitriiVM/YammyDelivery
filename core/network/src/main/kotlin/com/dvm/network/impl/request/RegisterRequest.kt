package com.dvm.network.impl.request

import kotlinx.serialization.Serializable

@Serializable
internal class RegisterRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String
)
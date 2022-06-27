package com.dvm.auth_impl.data.network.request

import kotlinx.serialization.Serializable

@Serializable
internal class ResetPasswordRequest(
    val email: String,
    val code: String,
    val password: String
)
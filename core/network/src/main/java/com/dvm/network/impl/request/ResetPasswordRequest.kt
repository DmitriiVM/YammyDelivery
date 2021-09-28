package com.dvm.network.impl.request

import kotlinx.serialization.Serializable

@Serializable
internal class ResetPasswordRequest(
    val email: String,
    val code: String,
    val password: String
)
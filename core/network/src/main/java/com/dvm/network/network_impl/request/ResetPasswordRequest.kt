package com.dvm.network.network_impl.request

internal class ResetPasswordRequest(
    val email: String,
    val code: String,
    val password: String
)
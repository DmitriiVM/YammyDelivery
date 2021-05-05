package com.dvm.network.impl.request

internal class ResetPasswordRequest(
    val email: String,
    val code: String,
    val password: String
)
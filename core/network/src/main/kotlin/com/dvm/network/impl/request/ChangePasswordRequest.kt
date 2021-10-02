package com.dvm.network.impl.request

internal class ChangePasswordRequest(
    val oldPassword: String,
    val newPassword: String
)
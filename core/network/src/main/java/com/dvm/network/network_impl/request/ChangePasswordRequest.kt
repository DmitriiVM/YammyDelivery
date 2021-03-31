package com.dvm.network.network_impl.request

internal class ChangePasswordRequest(
    val oldPassword: String,
    val newPassword: String
)
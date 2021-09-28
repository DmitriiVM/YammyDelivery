package com.dvm.network.impl.request

import kotlinx.serialization.Serializable

@Serializable
internal class ChangePasswordRequest(
    val oldPassword: String,
    val newPassword: String
)
package com.dvm.profile_impl.data.network.request

import kotlinx.serialization.Serializable

@Serializable
internal class ChangePasswordRequest(
    val oldPassword: String,
    val newPassword: String
)
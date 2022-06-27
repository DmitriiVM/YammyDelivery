package com.dvm.profile_impl.data.network.request

import kotlinx.serialization.Serializable

@Serializable
internal class EditProfileRequest(
    val firstName: String,
    val lastName: String,
    val email: String
)
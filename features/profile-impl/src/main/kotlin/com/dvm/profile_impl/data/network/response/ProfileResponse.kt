package com.dvm.profile_impl.data.network.response

import kotlinx.serialization.Serializable

@Serializable
data class ProfileResponse(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String
)
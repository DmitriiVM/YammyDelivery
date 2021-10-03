package com.dvm.network.api.response

import kotlinx.serialization.Serializable

@Serializable
data class ProfileResponse(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String
)
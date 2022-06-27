package com.dvm.auth_impl.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Token(
    val accessToken: String
)
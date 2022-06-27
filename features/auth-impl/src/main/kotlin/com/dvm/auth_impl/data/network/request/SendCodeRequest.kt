package com.dvm.auth_impl.data.network.request

import kotlinx.serialization.Serializable

@Serializable
internal class SendCodeRequest(
    val email: String,
    val code: String
)
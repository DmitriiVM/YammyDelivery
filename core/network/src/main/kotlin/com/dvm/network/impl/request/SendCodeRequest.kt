package com.dvm.network.impl.request

import kotlinx.serialization.Serializable

@Serializable
internal class SendCodeRequest(
    val email: String,
    val code: String
)
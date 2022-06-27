package com.dvm.auth_impl.data.network.request

import kotlinx.serialization.Serializable

@Serializable
internal class SendEmailRequest(
    val email: String
)
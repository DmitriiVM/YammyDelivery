package com.dvm.network.impl.request

import kotlinx.serialization.Serializable

@Serializable
internal class SendEmailRequest(
    val email: String
)
package com.dvm.network.impl.request

import kotlinx.serialization.Serializable

@Serializable
internal class CheckInputRequest(
    val address: String
)
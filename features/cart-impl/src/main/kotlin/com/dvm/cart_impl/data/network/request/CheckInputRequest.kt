package com.dvm.cart_impl.data.network.request

import kotlinx.serialization.Serializable

@Serializable
internal class CheckInputRequest(
    val address: String
)
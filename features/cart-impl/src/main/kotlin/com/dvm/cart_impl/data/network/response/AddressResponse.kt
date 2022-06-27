package com.dvm.cart_impl.data.network.response

import kotlinx.serialization.Serializable

@Serializable
data class AddressResponse(
    val city: String,
    val street: String,
    val house: String
)
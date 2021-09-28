package com.dvm.network.api.response

import kotlinx.serialization.Serializable

@Serializable
data class AddressResponse(
    val city: String,
    val street: String,
    val house: String
)
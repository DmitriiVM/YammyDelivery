package com.dvm.cart_impl.data.network.request

import kotlinx.serialization.Serializable

@Serializable
internal class CheckCoordinatesRequest(
    val lat: Double,
    val lon: Double
)
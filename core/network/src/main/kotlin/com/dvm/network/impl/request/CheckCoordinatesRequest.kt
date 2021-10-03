package com.dvm.network.impl.request

import kotlinx.serialization.Serializable

@Serializable
internal class CheckCoordinatesRequest(
    val lat: Double,
    val lon: Double
)
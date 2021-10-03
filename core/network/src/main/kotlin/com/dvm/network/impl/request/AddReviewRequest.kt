package com.dvm.network.impl.request

import kotlinx.serialization.Serializable

@Serializable
internal class AddReviewRequest(
    val rating: Int,
    val text: String
)
package com.dvm.menu_impl.data.network.request

import kotlinx.serialization.Serializable

@Serializable
internal class AddReviewRequest(
    val rating: Int,
    val text: String
)
package com.dvm.network.api.response

import kotlinx.serialization.Serializable

@Serializable
data class FavoriteResponse(
    val dishId: String,
    val favorite: Boolean
)
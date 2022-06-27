package com.dvm.menu_impl.data.network.response

import kotlinx.serialization.Serializable

@Serializable
data class FavoriteResponse(
    val dishId: String,
    val favorite: Boolean
)
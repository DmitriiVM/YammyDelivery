package com.dvm.menu_impl.data.network.request

import kotlinx.serialization.Serializable

@Serializable
internal class ChangeFavoriteRequest(
    val dishId: String,
    val favorite: Boolean
)
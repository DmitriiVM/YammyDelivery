package com.dvm.network.impl.request

import kotlinx.serialization.Serializable

@Serializable
internal class ChangeFavoriteRequest(
    val dishId: String,
    val favorite: Boolean
)
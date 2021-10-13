package com.dvm.order.map.model

import androidx.compose.runtime.Immutable

@Immutable
internal data class MapState(
    val address: String = "",
    val alert: Int? = null,
)
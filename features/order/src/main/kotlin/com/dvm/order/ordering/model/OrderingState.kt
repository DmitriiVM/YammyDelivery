package com.dvm.order.ordering.model

import androidx.compose.runtime.Immutable

@Immutable
internal data class OrderingState(
    val address: String = "",
    val progress: Boolean = false,
    val alert: Int? = null
)
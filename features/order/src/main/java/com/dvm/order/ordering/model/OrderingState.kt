package com.dvm.order.ordering.model

internal data class OrderingState(
    val address: String = "",
    val progress: Boolean = false,
    val alert: Int? = null
)
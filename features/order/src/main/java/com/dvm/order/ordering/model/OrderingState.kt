package com.dvm.order.ordering.model

import javax.annotation.concurrent.Immutable

@Immutable
internal data class OrderingState(
    val address: String = "",
    val progress: Boolean = false,
    val alert: String? = null
)
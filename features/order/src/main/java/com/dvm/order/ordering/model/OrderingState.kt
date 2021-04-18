package com.dvm.order.ordering.model

import javax.annotation.concurrent.Immutable

@Immutable
internal data class OrderingState(
    val networkCall: Boolean = false,
    val alertMessage: String? = null
)
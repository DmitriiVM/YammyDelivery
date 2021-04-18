package com.dvm.order.order.model

import javax.annotation.concurrent.Immutable

@Immutable
internal data class OrderState(
    val alertMessage: String? = null
)
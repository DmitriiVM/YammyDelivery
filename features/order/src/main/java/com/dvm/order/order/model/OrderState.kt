package com.dvm.order.order.model

import com.dvm.db.api.models.OrderWithItems

internal data class OrderState(
    val order: OrderWithItems? = null,
    val alert: String? = null,
    val orderAgainMessage: String? = null,
    val cancelMessage: String? = null,
    val progress: Boolean = false
)
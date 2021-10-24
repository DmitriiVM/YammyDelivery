package com.dvm.order.order.model

import androidx.compose.runtime.Immutable
import com.dvm.database.api.models.OrderWithItems

@Immutable
internal data class OrderState(
    val order: OrderWithItems? = null,
    val alert: Int? = null,
    val orderAgainMessage: Message? = null,
    val cancelMessage: Int? = null,
    val progress: Boolean = false
) {
    data class Message(
        val count: Int
    )
}
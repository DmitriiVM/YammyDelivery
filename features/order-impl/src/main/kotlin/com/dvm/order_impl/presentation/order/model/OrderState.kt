package com.dvm.order_impl.presentation.order.model

import androidx.compose.runtime.Immutable
import com.dvm.order_api.domain.model.OrderWithItems

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
package com.dvm.order.order.model

import com.dvm.db.api.models.OrderWithItems

internal data class OrderState(
    val order: OrderWithItems? = null,
    val alert: Int? = null,
    val orderAgainMessage: Message? = null,
    val cancelMessage: Int? = null,
    val progress: Boolean = false
){
    data class Message(
        val text: Int,
        val dish: Int,
        val count: Int
    )
}
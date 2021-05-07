package com.dvm.order.order.model

import com.dvm.db.api.models.OrderWithItems
import javax.annotation.concurrent.Immutable

@Immutable
internal data class OrderState(
    val order: OrderWithItems? = null,
    val alertMessage: String? = null,
    val actionAlertMessage: String? = null,
    val networkCall: Boolean = false
)
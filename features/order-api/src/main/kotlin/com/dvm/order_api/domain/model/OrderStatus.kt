package com.dvm.order_api.domain.model

data class OrderStatus(
    val id: String,
    val name: String,
    val cancelable: Boolean,
    val active: Boolean?
)
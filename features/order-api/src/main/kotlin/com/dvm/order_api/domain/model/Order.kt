package com.dvm.order_api.domain.model

import java.util.Date

data class Order(
    val id: String,
    val createdAt: Date,
    val total: Int,
    val address: String,
    val status: String,
)
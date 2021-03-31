package com.dvm.network.network_api.response

data class StatusResponse(
    val id: Int,
    val name: String,
    val cancelable: Boolean,
    val active: Boolean,
    val createdAt: Long,
    val updatedAt: Long
)
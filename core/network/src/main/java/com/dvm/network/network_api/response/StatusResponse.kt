package com.dvm.network.network_api.response

data class StatusResponse(
    val id: String,
    val name: String,
    val cancelable: Boolean,
    val active: Boolean,
    val createdAt: Long,
    val updatedAt: Long
)
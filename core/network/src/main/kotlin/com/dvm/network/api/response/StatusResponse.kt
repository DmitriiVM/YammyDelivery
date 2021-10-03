package com.dvm.network.api.response

import kotlinx.serialization.Serializable

@Serializable
data class StatusResponse(
    val id: String,
    val name: String,
    val cancelable: Boolean,
    val active: Boolean,
    val createdAt: Long,
    val updatedAt: Long
)
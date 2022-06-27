package com.dvm.menu_impl.data.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryResponse(
    @SerialName("categoryId")
    val id: String,
    val name: String,
    val order: Int,
    val parent: String? = null,
    val icon: String? = null,
    val active: Boolean,
    val createdAt: Long,
    val updatedAt: Long
)


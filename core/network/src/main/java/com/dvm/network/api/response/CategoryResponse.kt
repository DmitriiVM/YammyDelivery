package com.dvm.network.api.response

import com.google.gson.annotations.SerializedName


data class CategoryResponse(
    @SerializedName("categoryId")
    val id: String,
    val name: String,
    val order: Int,
    val parent: String? = null,
    val icon: String? = null,
    val active: Boolean,
    val createdAt: Long,
    val updatedAt: Long
)


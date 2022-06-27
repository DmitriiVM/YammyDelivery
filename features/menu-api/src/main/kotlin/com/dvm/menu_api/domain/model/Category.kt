package com.dvm.menu_api.domain.model

data class Category(
    val id: String,
    val name: String,
    val categoryOrder: Int,
    val parent: String?,
    val icon: String?,
    val active: Boolean?
)
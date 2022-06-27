package com.dvm.menu_api.domain.model

data class Subcategory(
    val id: String,
    val name: String,
    val parent: String?
)
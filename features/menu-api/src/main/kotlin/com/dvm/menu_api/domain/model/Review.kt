package com.dvm.menu_api.domain.model

class Review(
    val dishId: String,
    val author: String,
    val date: String,
    val rating: Int,
    val text: String? = null,
    val active: Boolean
)
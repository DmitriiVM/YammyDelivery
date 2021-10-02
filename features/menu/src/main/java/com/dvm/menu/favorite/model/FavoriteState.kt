package com.dvm.menu.favorite.model

import com.dvm.database.api.models.CardDishDetails

internal data class FavoriteState(
    val dishes: List<CardDishDetails> = emptyList(),
    val alert: Alert? = null,
) {
    data class Alert(
        val text: Int,
        val argument: Any
    )
}
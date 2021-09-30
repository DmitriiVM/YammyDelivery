package com.dvm.menu.favorite.model

import androidx.compose.runtime.Immutable
import com.dvm.database.api.models.CardDish

@Immutable
internal data class FavoriteState(
    val dishes: List<CardDish> = emptyList(),
    val alert: Alert? = null,
) {
    data class Alert(
        val text: Int,
        val argument: Any
    )
}
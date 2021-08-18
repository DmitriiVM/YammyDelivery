package com.dvm.menu.favorite.model

import androidx.compose.runtime.Immutable
import com.dvm.db.api.models.CardDishDetails

@Immutable
internal data class FavoriteState(
    val dishes: List<CardDishDetails> = emptyList(),
    val alert: String? = null,
)
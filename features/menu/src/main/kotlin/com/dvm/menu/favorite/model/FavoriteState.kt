package com.dvm.menu.favorite.model

import com.dvm.database.api.models.CardDishDetails
import com.dvm.utils.Text

internal data class FavoriteState(
    val dishes: List<CardDishDetails> = emptyList(),
    val alert: Text? = null,
)
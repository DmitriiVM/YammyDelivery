package com.dvm.menu.favorite.model

import androidx.compose.runtime.Immutable
import com.dvm.database.api.models.CardDishDetails
import com.dvm.utils.Text

@Immutable
internal data class FavoriteState(
    val dishes: List<CardDishDetails> = emptyList(),
    val alert: Text? = null,
)
package com.dvm.menu.favorite.model

import androidx.compose.runtime.Immutable
import com.dvm.database.api.models.CardDish
import com.dvm.utils.Text

@Immutable
internal data class FavoriteState(
    val dishes: List<CardDish> = emptyList(),
    val alert: Text? = null,
)
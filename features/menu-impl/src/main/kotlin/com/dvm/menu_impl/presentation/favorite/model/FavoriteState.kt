package com.dvm.menu_impl.presentation.favorite.model

import androidx.compose.runtime.Immutable
import com.dvm.menu_api.domain.model.CardDish
import com.dvm.utils.Text

@Immutable
internal data class FavoriteState(
    val dishes: List<CardDish> = emptyList(),
    val alert: Text? = null,
)
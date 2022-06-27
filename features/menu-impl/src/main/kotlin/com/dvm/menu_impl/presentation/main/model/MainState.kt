package com.dvm.menu_impl.presentation.search.model

import androidx.compose.runtime.Immutable
import com.dvm.menu_api.domain.model.CardDish
import com.dvm.utils.Text

@Immutable
internal data class MainState(
    val recommended: List<CardDish> = emptyList(),
    val best: List<CardDish> = emptyList(),
    val popular: List<CardDish> = emptyList(),
    val alert: Text? = null,
)
package com.dvm.menu.search.model

import androidx.compose.runtime.Immutable
import com.dvm.database.api.models.CardDish
import com.dvm.utils.Text

@Immutable
internal data class MainState(
    val recommended: List<CardDish> = emptyList(),
    val best: List<CardDish> = emptyList(),
    val popular: List<CardDish> = emptyList(),
    val alert: Text? = null,
)
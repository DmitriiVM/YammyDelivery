package com.dvm.menu.search.model

import androidx.compose.runtime.Immutable
import com.dvm.database.api.models.CardDishDetails
import com.dvm.utils.Text

@Immutable
internal data class MainState(
    val recommended: List<CardDishDetails> = emptyList(),
    val best: List<CardDishDetails> = emptyList(),
    val popular: List<CardDishDetails> = emptyList(),
    val alert: Text? = null,
)
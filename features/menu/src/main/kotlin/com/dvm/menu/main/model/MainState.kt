package com.dvm.menu.search.model

import androidx.compose.runtime.Immutable
import com.dvm.database.api.models.CardDish

@Immutable
internal data class MainState(
    val recommended: List<CardDish> = emptyList(),
    val best: List<CardDish> = emptyList(),
    val popular: List<CardDish> = emptyList(),
    val alert: Alert? = null,
) {
    data class Alert(
        val text: Int,
        val argument: Any? = null
    )
}
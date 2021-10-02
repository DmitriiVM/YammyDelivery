package com.dvm.menu.search.model

import com.dvm.database.api.models.CardDishDetails

internal data class MainState(
    val recommended: List<CardDishDetails> = emptyList(),
    val best: List<CardDishDetails> = emptyList(),
    val popular: List<CardDishDetails> = emptyList(),
    val alert: Alert? = null,
) {
    data class Alert(
        val text: Int,
        val argument: Any? = null
    )
}
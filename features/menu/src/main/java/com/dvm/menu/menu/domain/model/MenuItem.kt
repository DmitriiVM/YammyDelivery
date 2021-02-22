package com.dvm.menu.menu.domain.model

sealed class MenuItem {
    data class Item(
        val title: String,
        val imageUrl: String
    ) : MenuItem()

    data class Default(
        val title: String
    ) : MenuItem()

    object SpecialOffer : MenuItem()
}
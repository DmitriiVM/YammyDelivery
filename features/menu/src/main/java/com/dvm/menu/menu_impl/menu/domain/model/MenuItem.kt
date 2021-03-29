package com.dvm.menu.menu_impl.menu.domain.model

internal sealed class MenuItem {
    data class Item(
        val id: String,
        val title: String,
        val imageUrl: String
    ) : MenuItem()

    object SpecialOffer : MenuItem()
}
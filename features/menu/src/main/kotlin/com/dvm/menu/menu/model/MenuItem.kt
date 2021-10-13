package com.dvm.menu.menu.model

import androidx.compose.runtime.Immutable

@Immutable
internal sealed class MenuItem {
    data class Item(
        val id: String,
        val title: String,
        val imageUrl: String?
    ) : MenuItem()

    object SpecialOffer : MenuItem()
}
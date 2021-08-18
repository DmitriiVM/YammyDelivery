package com.dvm.menu.favorite.model

internal sealed class FavoriteEvent {
    data class OpenDish(val dishId: String) : FavoriteEvent()
    data class AddToCart(val dishId: String, val name: String) : FavoriteEvent()
    object DismissAlert : FavoriteEvent()
}
package com.dvm.menu.search.model

internal sealed class FavoriteEvent {
    data class DishClick(val dishId: String) : FavoriteEvent()
    data class AddToCart(val dishId: String, val name: String) : FavoriteEvent()
    object DismissAlert : FavoriteEvent()
}